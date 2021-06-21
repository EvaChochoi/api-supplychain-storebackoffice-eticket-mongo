/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.domain.mongo;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Parameters class
 */
@Document
@Value
@Builder
public class Parameters {

	private int refreshTime;

}
