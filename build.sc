import mill._
import mill.scalalib._
import mill.scalalib.scalafmt.ScalafmtModule
import $ivy.`com.lihaoyi::mill-contrib-bintray:$MILL_VERSION`
import mill.contrib.bintray.{BintrayPublishData, BintrayPublishModule}
import mill.scalalib.publish._

object bencode extends Module with BintrayPublishModule {
  def ivyDeps = Agg(
    ivy"org.scodec::scodec-core:1.11.4", 
    ivy"org.typelevel::cats-core:${Versions.cats}",
  )
  object test extends TestModule

  def bintrayOwner = "lavrov"
  def bintrayRepo = "maven"

  def pomSettings = PomSettings(
    description = "Bencode codec",
    organization = "com.github.torrentdam",
    url = "https://github.com/TorrentDam/bencode",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("TorrentDam", "bencode"),
    developers = Seq(
      Developer("lavrov", "Vitaly Lavrov","https://github.com/lavrov")
    )
  )

  override def bintrayPublishArtifacts: T[BintrayPublishData] = T {
    val original = super.bintrayPublishArtifacts()
    original.copy(payload = original.payload.filterNot(_._2.contains("javadoc")))
  }

  def publishVersion = "0.1.0"
}

trait Module extends ScalaModule with ScalafmtModule {
  def scalaVersion = "2.13.2"
  def scalacOptions = List(
    "-language:higherKinds",
    "-Ymacro-annotations",
  )

  def scalacPluginIvyDeps = Agg(
    ivy"org.typelevel:::kind-projector:0.11.0",
    ivy"com.olegpy::better-monadic-for:0.3.1",
  )
  trait TestModule extends Tests {
    def ivyDeps = Agg(
      ivy"com.eed3si9n.verify::verify:0.2.0",
    )
    def testFrameworks = Seq("verify.runner.Framework")
  }
}

object Versions {
  val cats = "2.2.0-RC2"
  val `scodec-bits` = "1.1.14"
}

