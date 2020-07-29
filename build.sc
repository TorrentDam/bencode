import mill._
import mill.scalalib._
import mill.scalalib.scalafmt.ScalafmtModule

object bencode extends Module {
  def ivyDeps = Agg(
    ivy"org.scodec::scodec-core:1.11.4", 
    ivy"org.typelevel::cats-core:${Versions.cats}",
  )
  object test extends TestModule
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

