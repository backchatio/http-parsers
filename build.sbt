import xml.Group
import scalariform.formatter.preferences._

name := "http-parsers"

version := "0.3.2-SNAPSHOT"

organization := "io.backchat.http"

scalaVersion := "2.9.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies <+= (scalaVersion) {
  case _ => "org.specs2" %% "specs2" % "1.7.1" % "test"
}

libraryDependencies ++= Seq(
  compilerPlugin("org.scala-tools.sxr" % "sxr_2.9.0" % "0.2.7"),
  "junit" % "junit" % "4.10" % "test",
  "org.parboiled" %   "parboiled-scala" % "1.0.2" % "compile"
)

resolvers += "ScalaTools Snapshots" at "http://scala-tools.org/repo-snapshots"

autoCompilerPlugins := true

homepage := Some(url("https://github.com/mojolly/http-parsers"))

startYear := Some(2011)

licenses := Seq(("Apache 2", url("http://www.apache.org/licenses/LICENSE-2.0.txt")))

pomExtra <<= (pomExtra, name, description) {(pom, name, desc) => pom ++ Group(
  <scm>
    <connection>scm:git:git://github.com/mojolly/http-parsers.git</connection>
    <developerConnection>scm:git:git@github.com:mojolly/http-parsers.git</developerConnection>
    <url>https://github.com/mojolly/http-parsers</url>
  </scm>
  <developers>
    <developer>
      <id>casualjim</id>
      <name>Ivan Porto Carrero</name>
      <url>http://flanders.co.nz/</url>
    </developer>
    <developer>
      <id>sirthias</id>
      <name>Matthias</name>
      <url>http://www.decodified.com/</url>
    </developer>
    <developer>
      <id>jrudolph</id>
      <name>Johannes Rudolph</name>
      <url>http://virtual-void.net/</url>
    </developer>
  </developers>
)}

packageOptions <+= (name, version, organization) map {
    (title, version, vendor) =>
      Package.ManifestAttributes(
        "Created-By" -> "Simple Build Tool",
        "Built-By" -> System.getProperty("user.name"),
        "Build-Jdk" -> System.getProperty("java.version"),
        "Specification-Title" -> title,
        "Specification-Version" -> version,
        "Specification-Vendor" -> vendor,
        "Implementation-Title" -> title,
        "Implementation-Version" -> version,
        "Implementation-Vendor-Id" -> vendor,
        "Implementation-Vendor" -> vendor
      )
  }

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { x => false }

seq(scalariformSettings: _*)

ScalariformKeys.preferences :=
  (FormattingPreferences()
        setPreference(IndentSpaces, 2)
        setPreference(AlignParameters, false)
        setPreference(AlignSingleLineCaseStatements, true)
        setPreference(DoubleIndentClassDeclaration, true)
        setPreference(RewriteArrowSymbols, true)
        setPreference(PreserveSpaceBeforeArguments, true)
        setPreference(IndentWithTabs, false))

(excludeFilter in ScalariformKeys.format) <<= (excludeFilter) (_ || "*Spec.scala")

testOptions in Test += Tests.Setup( () => System.setProperty("akka.mode", "test") )

testOptions in Test += Tests.Argument(TestFrameworks.Specs2, "console", "junitxml")

testOptions in Test <+= (crossTarget map { ct =>
 Tests.Setup { () => System.setProperty("specs2.junit.outDir", new File(ct, "specs-reports").getAbsolutePath) }
})

