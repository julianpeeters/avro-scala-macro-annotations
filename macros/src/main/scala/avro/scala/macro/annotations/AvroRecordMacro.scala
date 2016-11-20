package com.julianpeeters.avro.annotations

import record._
import ctorgen._
import namespacegen._
import methodgen._
import schemagen._

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros
import scala.annotation.{ compileTimeOnly, StaticAnnotation }

import org.apache.avro.Schema
import org.apache.avro.Schema.Field

import collection.JavaConversions._

object AvroRecordMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._

    //holds info about the fields of the annotee
    case class IndexedField(nme: TermName, tpe: Type, dv: Tree, idx: Int)

    //Extender
    def generateNewBaseTypes =  List( tq"org.apache.avro.specific.SpecificRecordBase")

    //CtorGen
    def generateNewCtors(indexedFields: List[IndexedField]): List[c.universe.DefDef] = {
      val dcpm = new { val context: c.type = c } with DefaultCtorParamMatcher
      val defaultParams = indexedFields.map(field => dcpm.matchDefaultParams(field.tpe, field.dv))
      val ctorGenerator = new {val context: c.type = c} with CtorGenerator
      ctorGenerator.toZeroArg(defaultParams) //Return a list of new CtorDefs
    }

    //NamespaceGen - getting namespace from the scala code enable reading from namespace-less schemas
    def generateNamespace =  NamespaceGenerator.probeNamespace(c)                         
    
    //SchemaGen - generates schemas and stores them
    def generateSchema(className: String, namespace: String, indexedFields: List[IndexedField]): Schema = {
      val fieldSchemaGenerator = new {val context: c.type = c} with FieldSchemaGenerator
      val avroFields = indexedFields.map(v => fieldSchemaGenerator.toAvroField(namespace, v.nme, v.tpe, v.dv))
      RecordSchemaGenerator.createSchema(className, namespace, avroFields)
    }

    //MethodGen - generates put, get, and getSchema needed to implement SpecificRecord for serialization
    def generateNewMethods(name: TypeName, indexedFields: List[IndexedField]) = {
      val exceptionCase = cq"""_ => new org.apache.avro.AvroRuntimeException("Bad index")"""

      val getDefCaseGenerator = new { val context: c.type = c } with GetDefCaseGenerator
      val getCases = indexedFields.map(f => getDefCaseGenerator.asGetCase(f.nme, f.tpe, f.idx)) :+ exceptionCase
      val getDef = q"""def get(field$$: Int): AnyRef = field$$ match {case ..$getCases}"""

      val getSchemaDef = q""" def getSchema: Schema = ${name.toTermName}.SCHEMA$$ """

      val putDefCaseGenerator = new { val context: c.type = c } with PutDefCaseGenerator
      val putCases = indexedFields.map(f => putDefCaseGenerator.asPutCase(f.nme, f.tpe, f.idx)) :+ exceptionCase
      val putDef = q"""def put(field$$: Int, value: scala.Any): Unit = { field$$ match {case ..$putCases}; () }"""

      List(getDef, getSchemaDef, putDef)
    }
    
    // Update ClassDef and Add Companion Object
    val result = { 
      // match the annotated class
      annottees.map(_.tree).toList match {
        // Update ClassDef and add companion object
        case classDef @ q"$mods class $className[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body };" :: tail => {

          //add an index field to the fields defined in the case class
          def indexField(f: ValDef) = {   
            val fieldName = f.name
            val fieldType = c.typecheck(q"type T = ${f.tpt}") match {
              case x @ TypeDef(mods, name, tparams, rhs)  => rhs.tpe
            }
            val defaultValue = f.rhs
            val position = first.indexWhere(f => f.name == fieldName)
            IndexedField(fieldName, fieldType, defaultValue, position)
          }
        
          //prep fields from annotee
          val indexedFields = first.map(f => indexField(f))

          // updates to the annotated class
          val newImports = List(q"import org.apache.avro.Schema")
          val newCtors   = generateNewCtors(indexedFields)              // a no-arg ctor so `newInstance()` can be used
          val newDefs    = generateNewMethods(className, indexedFields) // `get`, `put`, and `getSchema` methods 
          val newParents = generateNewBaseTypes ::: parents             // extend SpecificRecordBase
          val newBody    = body ::: newImports ::: newCtors ::: newDefs // add new members to the body

          // updates to the companion object
          val schema     = q"${generateSchema(className.toString, generateNamespace, indexedFields).toString}"
          val schemaVal  = q"lazy val SCHEMA$$ = new org.apache.avro.Schema.Parser().parse($schema)"

          val companionDef = tail match {
            // if there is no preexisiting companion then make one with a SCHEMA$ field
            case Nil => q"object ${className.toTermName} {$schemaVal}"
            // if there is a preexisting companion, add a SCHEMA$ field
            case List( moduleDef @ q"object $moduleName extends ..$companionParents { ..$moduleBody }") => {
              val newModuleBody = List(schemaVal) ::: moduleBody
              q"object ${className.toTermName} extends ..$companionParents { ..$newModuleBody }"
            }
          }

          // return an updated class def and companion def
          q"""$mods class $className[..$tparams](..$first)(...$rest) extends ..$newParents { $self => ..$newBody};
              $companionDef""" 
        }
      } 
    }
    c.Expr[Any](result)
  }
}

/**
 * From the Macro Paradise Docs...
 *
 * note the @compileTimeOnly annotation. It is not mandatory, but is recommended to avoid confusion.
 * Macro annotations look like normal annotations to the vanilla Scala compiler, so if you forget
 * to enable the macro paradise plugin in your build, your annotations will silently fail to expand.
 * The @compileTimeOnly annotation makes sure that no reference to the underlying definition is
 * present in the program code after typer, so it will prevent the aforementioned situation
 * from happening.
 */
@compileTimeOnly("Enable Macro Paradise for Expansion of Annotations via Macros.")
class AvroRecord extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro AvroRecordMacro.impl
}
