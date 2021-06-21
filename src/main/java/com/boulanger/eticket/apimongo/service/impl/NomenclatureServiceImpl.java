/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.service.impl;

import com.boulanger.eticket.apimongo.domain.mongo.Nomenclature;
import com.boulanger.eticket.apimongo.repository.mongo.NomenclatureMongoRepository;
import com.boulanger.eticket.apimongo.service.NomenclatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.boulanger.eticket.apimongo.domain.Constants.NomenclatureData.SALES_ORGANIZATION_OCFR;
import static com.boulanger.eticket.apimongo.domain.Constants.NomenclatureData.TYPE;

/**
 * Nomenclature Services implementation
 */
@Service("nomenclatureservice")
@RequiredArgsConstructor
public class NomenclatureServiceImpl implements NomenclatureService {

	private final NomenclatureMongoRepository nomenclatureMongoRepository;
	private static final int LEVEL_NUMBER = 3;

	/**
	 * Get a list of {@link Nomenclature}
	 *
	 * @param externalNodeId externalNodeId
	 * @return a list of {@link Nomenclature}
	 */
	@Override public List<Nomenclature> getNomenclatures(String externalNodeId) {
		List<Nomenclature> nomenclatures = new ArrayList<>();
		for (int i = 0; i < LEVEL_NUMBER; i++) {
			String nomenclatureId = externalNodeId.substring(0, (2 + i * 2));
			nomenclatures.add(nomenclatureMongoRepository.findByExternalNodeId(nomenclatureId, TYPE, SALES_ORGANIZATION_OCFR));
		}
		return nomenclatures;
	}
}
