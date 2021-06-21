/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.service.impl;

import com.boulanger.eticket.apimongo.domain.mongo.StocksLabels;
import com.boulanger.eticket.apimongo.repository.mongo.StocksLabelsMongoRepository;
import com.boulanger.eticket.apimongo.service.StocksLabelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Stocks Labels Services implementation
 */
@Service("stockslabelsservice")
@RequiredArgsConstructor
public class StocksLabelsServiceImpl implements StocksLabelsService {

	private final StocksLabelsMongoRepository stocksLabelsMongoRepository;

	/**
	 * Return the stocks label from the parameter collection in Mongo database
	 *
	 * @return StocksLabels
	 */
	@Override public StocksLabels getStocksLabels() {
		return stocksLabelsMongoRepository.findAll().get(0);

	}
}
