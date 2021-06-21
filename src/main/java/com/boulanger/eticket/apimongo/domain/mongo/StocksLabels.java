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
 * Stock Labels class
 */
@Document(collection = "stockslabels")
@Value
@Builder
public class StocksLabels {

	private String newLabel;
	private String unwrappedLabel;
	private String reconditionedLabel;
	private String exhibitLabel;
}
