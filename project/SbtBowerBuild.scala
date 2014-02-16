import sbt._
import Keys._

object Publish{
  object TargetRepository {
    def scmio: Project.Initialize[Option[sbt.Resolver]] = version { (version: String) =>
      val rootDir = "/srv/maven/"
      val path =
        if (version.trim.endsWith("SNAPSHOT")) 
          rootDir + "snapshots/" 
        else 
          rootDir + "releases/" 
      Some(Resolver.sftp("scm.io intern repo", "scm.io", 44144, path))
    }
  }
  lazy val settings = Seq(
    publishMavenStyle := true,
    publishTo <<= TargetRepository.scmio,
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    homepage := Some(url("http://scm.io")),
    pomExtra := extraPom)

	def extraPom = (
		<url></url>
		<licenses>
		  <license>
		    <name>Apache 2.0</name>
		    <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
		    <distribution>repo</distribution>
		  </license>
		</licenses>
		<scm>
		  <url>git@github.com:scalableminds/sbt-bower-simple.git</url>
		  <connection>scm:git:git@github.com:scalableminds/sbt-bower-simple.git</connection>
		</scm>
		<developers>
		  <developer>
		    <id>tmbo</id>
		    <name>Tom Bocklisch</name>
		    <url>http://github.com/tmbo</url>
		  </developer>
		</developers>
	)
}

object SbtBowerBuild extends Build with BuildExtra {

  val projectVersion = "1.0.0"

  val projectName = "sbt-bower-simple"

	lazy val sbtBower = Project(projectName, file("."), settings = projectSettings)

	lazy val projectSettings: Seq[Project.Setting[_]] = Defaults.defaultSettings ++ Seq(
		sbtPlugin := true,
		name := projectName,
		organization := "com.scalableminds",
		version := projectVersion,
		scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")
	) ++ Publish.settings
}