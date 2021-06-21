/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.service.impl;

import com.boulanger.eticket.apimongo.domain.mongo.Parameters;
import com.boulanger.eticket.apimongo.repository.mongo.ParametersMongoRepository;
import com.boulanger.eticket.apimongo.service.ParametersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Parameters Services implementation
 */
@Service("paramtersservice")
@RequiredArgsConstructor
public class ParametersServiceImpl implements ParametersService {

	private final ParametersMongoRepository parametersMongoRepository;

	@Override public Parameters getParameters() {
		return parametersMongoRepository.findAll().get(0);

	}
}
