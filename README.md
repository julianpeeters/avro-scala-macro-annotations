##How to use until pull request is accepted:

First of all:
The old type of schemas must have the `.avro` file extension.
The new type of schemas must have the `.avsc` file extension.

Clone this project. Then go to the root and run `sbt publishLocal`

After that you can use it in your project with this dependency:
        

libraryDependencies += "com.julianpeeters" %%
"avro-scala-macro-annotations" % "0.2"
