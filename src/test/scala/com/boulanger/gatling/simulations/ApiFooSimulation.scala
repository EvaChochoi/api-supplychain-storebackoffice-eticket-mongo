/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2020.
 *  (C) Copyright Boulanger S.A., 2020
 * -----------------------------------------------------------------
 */

package com.boulanger.gatling.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder.toHttpProtocol
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

import scala.concurrent.duration._

/**
  * Simulation gatling
  *
  * Run MainGatling to launch
  *
  */
class ApiFooSimulation extends Simulation {

  /**
    * CONSTANTS
    */
  val API_KEY_HEADER = "X-Api-Key"
  var random = new scala.util.Random
  System.setProperty("jsse.enableSNIExtension", "false")
  //val feeder = tsv(filename).circular

  val signKEy = System.getProperty("hs256_key", "achanger")
  var boulangerTokenGenerator = new BoulangerTokenGenerator(signKEy)

  /**
    * PARAMETERS
    *
    * Use GATLING_OPTS to override
    *
    */
  var scenarioUri = System.getProperty("private_uri", "http://localhost:8080")
  var warmUpInitialNbUsers = Integer.getInteger("warm_up_initial_users", 1).toInt
  var warmUpDuration = Integer.getInteger("warm_up_duration", 1).toInt
  var nbUsers = Integer.getInteger("users1", 20).toInt
  var duration = Integer.getInteger("duration1", 1).toInt
  var apikey = System.getProperty("api_key", "")

  // DEBUG
  println("================================")
  printf("param scenario_uri = '%s'", scenarioUri)
  println()
  printf("param warm_up_initial_users = '%s'", warmUpInitialNbUsers)
  println()
  printf("param warm_up_duration (up to stage1) = '%s'", warmUpDuration)
  println()
  printf("param users1 (amount of users for stage1) = '%s'", nbUsers)
  println()
  printf("param duration1 (in minutes) = '%s'", duration)
  println()
  printf("param apikey = '%s'", apikey)
  println()
  println("================================")

  /**
    * REQUESTS
    */
  val getFoo = exec(session => getRandomFooId(session))
    .exec(http("GET RANDOM FOO")
      .get("/foos/${fooId}")
      .check(status.is(200))
      .check(jsonPath("$.fooId").is("${fooId}")))

  val getFoos = exec(http("GET 10 FOOS")
    .get("/foos")
    /**
     * Exemple generation JWT (ne pas utiliser en local ; il faut passer par l'APIM si utilisation X-Auth headers)
     *
     * .header("Authorization", session => "Bearer " + boulangerTokenGenerator.getRandomCollaboratorToken(session, "${employee_id}", List("bl:customer.loyalty:read:all", "bl:customer.sale:read:all")))
     */
    .check(status.is(200))
    .check(jsonPath("$..foo").count.is(10)))

  /**
    * METHODS
    */
  val getRandomFooId = (session: Session) => {
    session.set("fooId", random.nextInt(100))
  }

  /**
    * SCENARIO
    */
  var sncTest = scenario("API FOO V1 - Bench")
    //.feed(feeder)
    .exec(randomSwitch(50d -> exec(getFoo), 50d -> exec(getFoos)))


  /**
    * SETUP
    */
  val httpProtocol =
    http
      .baseUrl(scenarioUri)
      .disableWarmUp
      .acceptHeader("application/json")
      .userAgentHeader("Gatling")
      .header(API_KEY_HEADER, apikey)
      .header("Content-Type", "application/json")
  //.proxy(Proxy("proxy-internet.localnet", 3128).httpsPort(3128)) // uncomment for local

  setUp(
    sncTest.inject(
      rampUsersPerSec(warmUpInitialNbUsers) to nbUsers during (warmUpDuration minutes),
      constantUsersPerSec(nbUsers) during (duration minutes))
  ).protocols(httpProtocol)

}
