/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.service;

/**
 * Translation Service
 */
public interface TranslationService {
	/**
	 * Get the translated label for an ID and in a specific language
	 * @param id ID
	 * @param lang language
	 * @return the translated label
	 */
	String getTranslatedLabelByIdAndLang(String id, String lang);

}
