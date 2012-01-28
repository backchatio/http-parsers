// import scalariform.formatter.preferences._

name := "http-parsers"

version := "0.3.0"

organization := "io.backchat.http"

scalaVersion := "2.9.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies <+= (scalaVersion) {
  case _ => "org.specs2" %% "specs2" % "1.6.1" % "test"
}

libraryDependencies ++= Seq(
  compilerPlugin("org.scala-tools.sxr" % "sxr_2.9.0" % "0.2.7"),
  "junit" % "junit" % "4.10" % "test",
  "org.parboiled" %   "parboiled-scala" % "1.0.2" % "compile"
)

// seq(scalariformSettings: _*)

// ScalariformKeys.preferences := (FormattingPreferences()
//        setPreference(IndentSpaces, 2)
//        setPreference(AlignParameters, true)
//        setPreference(AlignSingleLineCaseStatements, true)
//        setPreference(DoubleIndentClassDeclaration, true)
//        setPreference(RewriteArrowSymbols, true)
//        setPreference(PreserveSpaceBeforeArguments, true))


resolvers += "ScalaTools Snapshots" at "http://scala-tools.org/repo-snapshots"

autoCompilerPlugins := true

testOptions := Seq(
        Tests.Argument("console", "junitxml"))

testOptions <+= crossTarget map { ct =>
  Tests.Setup { () => System.setProperty("specs2.junit.outDir", new File(ct, "specs-reports").getAbsolutePath) }
}

