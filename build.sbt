name := "scala3-samples"

val scalaV = "3.1.2"

ThisBuild / organization := "ru.ekuzmichev"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := scalaV

lazy val root = (project in file("."))
  .aggregate(
    lang
  )

lazy val lang =
  project
    .settings(
      libraryDependencies ++= Seq(),
      Seq(scalacOptions ++= commonScalaOptions)
    )

lazy val commonScalaOptions =
  Seq(
//    "-Xfatal-warnings",
//    "-Ypartial-unification",
//    "-feature",
//    "-deprecation",
//    "-unchecked",
//    "-language:postfixOps",
//    "-language:higherKinds"
  )

lazy val libs = new {

}
