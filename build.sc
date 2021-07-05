import $ivy.`com.lihaoyi::mill-contrib-artifactory:$MILL_VERSION`

import mill._
import mill.scalalib._
import mill.scalalib.scalafmt.ScalafmtModule
import mill.scalalib.publish._
import mill.contrib.artifactory.ArtifactoryPublishModule

object bencode extends Module with Publishing {
  def ivyDeps = Agg(
    ivy"org.scodec::scodec-core:${Versions.scodec}",
    ivy"org.typelevel::cats-core:${Versions.cats}",
  )
  object test extends TestModule
}

trait Publishing extends ArtifactoryPublishModule {
  import mill.scalalib.publish._

  def artifactoryUri  = "https://maven.pkg.github.com/TorrentDam/bencode"

  def artifactorySnapshotUri = ""

  def pomSettings = PomSettings(
    description = "Bencode codecs",
    organization = "com.github.torrentdam",
    url = "https://github.com/TorrentDam/bencode",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("TorrentDam", "bencode"),
    developers = Seq(
      Developer("lavrov", "Vitaly Lavrov","https://github.com/lavrov")
    )
  )

  def publishVersion = T {
    T.ctx.env.getOrElse("GITHUB_REF", "1.0.0")
  }
}

trait Module extends ScalaModule with ScalafmtModule {
  def scalaVersion = "3.0.0"
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

