import mill._
import mill.api.Result
import mill.scalalib._
import mill.scalanativelib._
import mill.scalalib.scalafmt.ScalafmtModule

object bencode extends Module with Publishing {
  def ivyDeps = Agg(
    ivy"org.scodec::scodec-core::${Versions.scodec}",
    ivy"org.typelevel::cats-core::${Versions.cats}",
  )
  object test extends TestModule

  object native extends NativeModule with Publishing {
    def millSourcePath = bencode.millSourcePath
    def ivyDeps = bencode.ivyDeps
    object test extends NativeTestModule
  }
}

trait Publishing extends PublishModule {
  import mill.scalalib.publish._

  def artifactName = "bencode"

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
  def scalaVersion = "3.1.3"
  trait TestModule extends Tests {
    def ivyDeps = Agg(
      ivy"com.eed3si9n.verify::verify::1.0.0",
    )
    def testFrameworks = Seq("verify.runner.Framework")
  }
}

trait NativeModule extends Module with ScalaNativeModule {
  def scalaNativeVersion = "0.4.5"

  trait NativeTestModule extends TestModule with Tests
}

object Versions {
  val cats = "2.8.0"
  val `scodec` = "2.2.0"
}

