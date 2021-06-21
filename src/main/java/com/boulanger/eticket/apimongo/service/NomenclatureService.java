/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.service;

import com.boulanger.eticket.apimongo.domain.mongo.Nomenclature;

import java.util.List;

/**
 * Nomenclature Service
 */
public interface NomenclatureService {

	/**
	 * Get the nomenclatures data form this externalNodeId
	 * @param externalNodeId externalNodeId
	 * @return a list of {@link Nomenclature}
	 */
	List<Nomenclature> getNomenclatures(String externalNodeId);


}
