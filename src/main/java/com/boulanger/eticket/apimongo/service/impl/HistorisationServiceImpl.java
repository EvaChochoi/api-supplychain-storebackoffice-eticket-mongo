/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.service.impl;

import com.boulanger.eticket.apimongo.domain.mongo.Historisation;
import com.boulanger.eticket.apimongo.repository.mongo.HistorisationMongoRepository;
import com.boulanger.eticket.apimongo.service.HistorisationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Historisation Service implementation
 */
@Component
@Service("histoservice")
@Slf4j
@RequiredArgsConstructor
public class HistorisationServiceImpl implements HistorisationService {

	private final HistorisationMongoRepository repository;

	@Override public void register(Historisation historisation) {
		repository.save(historisation);
	}
}
