/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2020.
 *  (C) Copyright Boulanger S.A., 2020
 * -----------------------------------------------------------------
 */

package com.boulanger.gatling.simulations

import java.nio.file.Path

import io.gatling.app.Gatling
import io.gatling.commons.util.PathHelper._
import io.gatling.core.config.GatlingPropertiesBuilder

object MainGatling extends App {

  val gatlingConfUrl: Path = getClass.getClassLoader.getResource("gatling.conf")
  val projectRootDir = gatlingConfUrl.ancestor(3)
  val mavenResourcesDirectory = projectRootDir / "src" / "test" / "resources"
  val mavenTargetDirectory = projectRootDir / "target"
  val mavenBinariesDirectory = mavenTargetDirectory / "test-classes"
  val resultsDirectory = mavenTargetDirectory / "gatling"

  /**
    * run gatling engine and show bench menu
    */
  val props = new GatlingPropertiesBuilder()
    .resourcesDirectory(this.mavenResourcesDirectory.toString)
    .resultsDirectory(this.resultsDirectory.toString)
    .binariesDirectory(this.mavenBinariesDirectory.toString)
  Gatling.fromMap(props.build)
}
