/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.domain.mongo;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Translation class
 */
@Document
@Value
@Builder
public class Translation {

	@Id
	private String id;
	private List<Line> lines;

	/**
	 * Line form Translation
	 */
	@Value
	@Builder
	public static class Line {
		private String lang;
		private String label;
	}
}
