import mill._
import mill.api.Result
import mill.scalalib._
import mill.scalalib.scalafmt.ScalafmtModule

object bencode extends Module with Publishing {
  def ivyDeps = Agg(
    ivy"org.scodec::scodec-core:${Versions.scodec}",
    ivy"org.typelevel::cats-core:${Versions.cats}",
  )
  object test extends TestModule
}

trait Publishing extends PublishModule {
  import mill.scalalib.publish._

  def sonatypeUri: String = "https://s01.oss.sonatype.org/service/local"

  def pomSettings = PomSettings(
    description = "Bencode codecs",
    organization = "io.github.torrentdam.bencode",
    url = "https://github.com/TorrentDamDev/bencode",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("TorrentDamDev", "bencode"),
    developers = Seq(
      Developer("lavrov", "Vitaly Lavrov","https://github.com/lavrov")
    )
  )

  def publishVersion = T.input {
    T.ctx.env.get("VERSION") match {
      case Some(version) => Result.Success(version)
      case None => Result.Failure("VERSION env variable is undefined")
    }
  }
}

trait Module extends ScalaModule with ScalafmtModule {
  def scalaVersion = "3.2.0"
  trait TestModule extends Tests {
    def ivyDeps = Agg(
      ivy"com.eed3si9n.verify::verify:1.0.0",
    )
    def testFrameworks = Seq("verify.runner.Framework")
  }
}

object Versions {
  val cats = "2.6.1"
  val `scodec` = "2.0.0"
}

