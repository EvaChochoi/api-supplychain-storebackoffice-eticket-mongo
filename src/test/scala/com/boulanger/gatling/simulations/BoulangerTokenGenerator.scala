/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2020.
 *  (C) Copyright Boulanger S.A., 2020
 * -----------------------------------------------------------------
 */

package com.boulanger.gatling.simulations

import java.time.Clock
import java.util.UUID

import io.gatling.core.session.{Expression, Session}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}

import scala.collection.immutable.List

class BoulangerTokenGenerator(hs256Key: String) {

  implicit val clock: Clock = Clock.systemUTC

  def getRandomCollaboratorToken(session: Session, employeeIdExp: Expression[String], scopes: List[String]): String = {
    getRandomCollaboratorToken(employeeIdExp.apply(session).toOption.get, scopes)
  }

  def getRandomCollaboratorToken(employeeId: String, scopes: List[String]): String = {
    getToken(JwtClaim()
      .+("employee_id", employeeId)
      .+("employee_login", "BL" + employeeId)
      .+("email", "BL" + employeeId + "@boulanger.com")
      .+("scopes", scopes.mkString(" ")))
  }

  def getRandomFinalCustomerToken(session: Session, finalCustomerIdExp: Expression[String], scopes: List[String]): String = {
    val finalCustomerId = finalCustomerIdExp.apply(session).toOption.get
    getRandomFinalCustomerToken(finalCustomerId, scopes)
  }

  def getRandomFinalCustomerToken(finalCustomerId: String, scopes: List[String]): String = {
    getToken(JwtClaim()
      .+("external_id", finalCustomerId)
      .+("email", finalCustomerId + "@boulanger.com")
      .+("scopes", scopes.mkString(" ")))
  }

  def getToken(jwtClaim: JwtClaim): String = {
    Jwt.encode(jwtClaim
      .about(UUID.randomUUID().toString)
      .by("https://gatling-boulanger")
      .to("gatling")
      .+("domain", "boulanger")
      .+("given_name", "John")
      .+("family_name", "Do")
      .issuedNow.expiresIn(3600), hs256Key, JwtAlgorithm.HS256)
  }

}
