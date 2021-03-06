import Dependencies.Modules._

name := "reactive-storage"

version := "0.1"

scalaVersion := "2.12.8"

lazy val flerken =
  (project in file("."))
    .settings(noPublishSettings)
    .aggregate(
      reactiveWorker,
      httpWorker
    )

lazy val worker = (project in file("worker")).settings(
  libraryDependencies ++= Worker.dependencies
).settings(compilerSettings)

lazy val reactiveWorker = (project in file("reactive-worker")).settings(
  libraryDependencies ++= ReactiveWorker.dependencies
).settings(compilerSettings)
  .dependsOn(worker)

lazy val httpWorker = (project in file("http-worker"))
  .settings(
    libraryDependencies ++= HttpWorker.dependencies
  ).settings(compilerSettings)
  .dependsOn(worker, reactiveWorker)

lazy val noPublishSettings = Seq(
  publish := {},
  skip in publish := true,
  publishLocal := {},
  publishArtifact in Test := false
)

lazy val compilerSettings = {
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-language:postfixOps",              //Allow postfix operator notation, such as `1 to 10 toList'
    "-language:implicitConversions",
    "-language:higherKinds",
    "-Ypartial-unification",
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
    "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
    "-Ywarn-numeric-widen",              // Warn when numerics are widened.
    "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals",              // Warn if a local definition is unused.
    "-Ywarn-unused:params",              // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates",            // Warn if a private member is unused.
    "-Ywarn-value-discard",               // Warn when non-Unit expression results are unused.
    "-Ywarn-unused:imports",
    "-Xfatal-warnings"
  )
}