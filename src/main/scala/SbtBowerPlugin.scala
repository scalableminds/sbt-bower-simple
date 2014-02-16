import sbt._
import Keys._

object SimpleBowerKeys {
  val bowerPath = SettingKey[String]("bower-path","where bower is installed")
}

object SbtSimpleBowerPlugin extends Plugin {
  object Colors {

    import scala.Console._

    lazy val isANSISupported = {
      Option(System.getProperty("sbt.log.noformat")).map(_ != "true").orElse {
        Option(System.getProperty("os.name"))
          .map(_.toLowerCase)
          .filter(_.contains("windows"))
          .map(_ => false)
      }.getOrElse(true)
    }
    
    def green(str: String): String = if (isANSISupported) (GREEN + str + RESET) else str
  }


  import SimpleBowerKeys._

  private def bowerGenerateTask: Def.Initialize[Task[Seq[File]]] = (bowerPath, baseDirectory, streams) map { (bower, base, s) =>
    try{
      Process( bower :: "install" :: Nil, base ) ! s.log
      s.log.info(Colors.green("Bower install finished"))
    } catch {
      case e: java.io.IOException =>
        s.log.error("Bower couldn't be found. Please set the configuration key 'SimpleBowerKeys.bowerPath' properly. " + e.getMessage)
    }
    Seq()
  }

  lazy val bowerSettings: Seq[Setting[_]] = Seq(
    bowerPath := "/usr/local/share/npm/bin/bower",
    resourceGenerators in Compile <+= bowerGenerateTask
  )
}