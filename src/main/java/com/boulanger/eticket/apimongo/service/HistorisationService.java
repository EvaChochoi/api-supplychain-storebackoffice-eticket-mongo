/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.service;

import com.boulanger.eticket.apimongo.domain.mongo.Historisation;

/**
 * Historisation Services
 */
public interface HistorisationService {
	/**
	 * Register this historisation entry in database
	 * @param historisation {@link Historisation}
	 */
	void register(Historisation historisation);

}
