/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.domain.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 *Nomenclature class
 */
@Document
@Value
@Builder
public class Nomenclature {

	@Id
	private String id;
	private String categoryId;
	private String salesOrganization;
	private String externalNodeId;
	private String parentId;
	private String type;
	private Label label;
	private Characteristics characteristics;

	/**
	 * Label from Nomenclature
	 */
	@Value
	public static class Label {
		@JsonProperty("fr_FR")
		String fr_FR;
	}

	/**
	 * Characteristics from Nomenclature
	 */
	@Value
	public static class Characteristics {
		LongDescription longDescription;
		Image image;
		List<String> visible;
		boolean clickToChatAllowed;
		List<String> affiliations;
		boolean publishable;
		String zanoxId;
		ShortDescription shortDescription;
		String type;
		float bundleDiscountPercentageDisplayThreshold;
		float sequence;
		String displayType;
		float bundleDiscountEuroDisplayThreshold;
		String creditId;
		boolean system;
		SiteUrlPathSlug siteUrlPathSlug;
		RedirectionUrl redirectionUrl;

		/**
		 * LongDescription from Nomenclature
		 */
		@Value
		public static class LongDescription {
			@JsonProperty("fr_FR")
			String frFR;
		}

		/**
		 * Image from Nomenclature
		 */
		@Value
		public static class Image {
			@JsonProperty("fr_FR")
			String frFR;
		}

		/**
		 * ShortDescription from Nomenclature
		 */
		@Value
		public static class ShortDescription {
			@JsonProperty("fr_FR")
			String frFR;
		}

		/**
		 * SiteUrlPathSlug from Nomenclature
		 */
		@Value
		public static class SiteUrlPathSlug {
			@JsonProperty("fr_FR")
			String frFR;
		}

		/**
		 * RedirectionUrl from Nomenclature
		 */
		@Value
		public static class RedirectionUrl {
			@JsonProperty("fr_FR")
			String frFR;
		}

	}

}
