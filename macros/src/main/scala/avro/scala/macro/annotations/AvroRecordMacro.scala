package com.julianpeeters.avro.annotations

import matchers.DefaultParamMatcher
import scala.reflect.macros.Context


import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import com.gensler.scalavro.types._
import org.apache.avro.util.Utf8


object AvroRecordMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    
    import c.universe._
    import Flag._

    case class FieldData(nme: TermName, tpt: Tree, idx: Int)//holds info about the fields of the annotee

    //Extender
    def generateNewBaseTypes =  List( tq"SpecificRecordBase", tq"SpecificRecord" )

    //CtorGen
    def generateNewCtors(indexedFields: List[FieldData]) = {
      def asCtorParam(typeName: String, c: Context): c.universe.Tree = {
        if (typeName.endsWith("]")) DefaultParamMatcher.asParameterizedDefaultParam(typeName, c)
        else  DefaultParamMatcher.asDefaultParam(typeName, c)}
      val defaultParams = indexedFields.map(field => asCtorParam(field.tpt.toString, c)) //toString to reuse DefaultParamMatcher
      val newCtorDef = q"""def this() = this(..$defaultParams)"""
      val defaultCtorPos = c.enclosingPosition //thanks to Eugene Burmako for the workaround to position the ctor correctly
      val newCtorPos = defaultCtorPos
        .withEnd(defaultCtorPos.endOrPoint + 1)
        .withStart(defaultCtorPos.startOrPoint + 1)
        .withPoint(defaultCtorPos.point + 1)
      List( atPos(newCtorPos)(newCtorDef) ) //Return a list of new CtorDefs
    }

    //MethodGen
    def generateNewMethods(indexedFields: List[FieldData]) = {
      val getDef = { //expands to cases that are to be used in a pattern match, e.g. case 1 => username.asInstanceOf[AnyRef]
        def asGetCases(fd: FieldData) = cq"""pos if (pos == ${fd.idx}) => ${fd.nme}.asInstanceOf[AnyRef]"""
        val getCases = indexedFields.map(f => asGetCases(f)) :+ cq"""_ => new org.apache.avro.AvroRuntimeException("Bad index")"""
        q"""def get(field: Int): AnyRef = field match {case ..$getCases}"""
      }

      val getSchemaDef = q"""def getSchema: Schema = new Schema.Parser().parse(AvroType[Twitter_Schema].schema.toString)"""

      val putDef = {//expands to cases used in a pattern match, e.g. case 1 => this.username = value.asInstanceOf[String]
        def asPutCases(fd: FieldData) = {
          cq"""pos if (pos == ${fd.idx}) => this.${fd.nme} = (value match {
            case utf8: org.apache.avro.util.Utf8 => utf8.toString
            case x => x
          }).asInstanceOf[${fd.tpt}] """
        }
        val putCases = indexedFields.map(f => asPutCases(f)) :+ cq"""_ => new org.apache.avro.AvroRuntimeException("Bad index")"""
        q"""def put(field: Int, value: scala.Any): Unit = field match {case ..$putCases}"""
      }
      List(getDef, getSchemaDef, putDef)
    }

    //Update ClassDef and ModuleDef
    val result = { 
      annottees.map(_.tree).toList match {

        //Update ClassDef
        case q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {

          def indexed(fields: List[ValDef]) = { //adds index to the field name and type, loads into a FieldData case class
            (fields.map(_.name), first.map(_.tpt), 0 to fields.length-1)
              .zipped 
              .toList 
              .map(f => FieldData(f._1, f._2, f._3))} //(name, type, index)

          val newCtors       = generateNewCtors(indexed(first))   //a no-arge ctor so `newInstance()` can be used
          val newDefs        = generateNewMethods(indexed(first)) //`get`, `put`, and `getSchema` methods 

          val newParents     = parents ::: generateNewBaseTypes   //extend SpecificRecord and SpecificRecordBase
          val newBody        = body ::: newCtors ::: newDefs      //add new members to the body 

          //return an updated class def
          q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$newParents { $self => ..$newBody }" 
        }
      } 
    }

    c.Expr[Any](result)
  }
}

class AvroRecord extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro AvroRecordMacro.impl
}
