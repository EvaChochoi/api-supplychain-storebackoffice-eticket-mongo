/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.domain.mongo;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

/**
 * Historisation class
 */
@Value
@Builder
public class Historisation {
	private StoredTicket storedTicket;
	private String type;
	private Instant date;

}
