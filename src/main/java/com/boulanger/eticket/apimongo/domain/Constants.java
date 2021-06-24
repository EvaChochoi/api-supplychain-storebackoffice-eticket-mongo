/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.domain;

/**
 * Constants class
 */
public final class Constants {

	private static final String CLASS_STATUS = "Utility class";
	/**
	 * Ticket status
	 */
	public static final class TicketStatus {
		public static final String LIBERATION = "Libération";
		public static final String PRIS_EN_COMPTE = "Pris en compte";
		public static final String ACQUITTEMENT_OK = "Acquittement OK";
		public static final String ACQUITTEMENT_KO = "Acquittement KO";
		public static final String TERMINE = "Terminé";


		private TicketStatus() {
			throw new IllegalStateException(CLASS_STATUS);
		}
	}

	/**
	 * User claims
	 */
	public static final class Claims {
		public static final String EMPLOYEE_ID = "employee_id";
		public static final String GIVEN_NAME = "given_name";
		public static final String FAMILY_NAME = "family_name";

		private Claims() {
			throw new IllegalStateException(CLASS_STATUS);
		}
	}

	/**
	 * Nomenclature data
	 */
	public  static final class NomenclatureData {
		public static final String TYPE = "SAP";
		public static final String SALES_ORGANIZATION_OCFR = "OCFR";

		private NomenclatureData() {
			throw new IllegalStateException(CLASS_STATUS);
		}
	}


	private Constants() {
		throw new IllegalStateException(CLASS_STATUS);
	}

}


