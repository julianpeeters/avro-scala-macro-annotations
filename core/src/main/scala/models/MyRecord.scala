package models
@nestedInner
case class MyRec//(x: String)
@nestedOuter
case class MyRecord//(myRec: MyRec)

