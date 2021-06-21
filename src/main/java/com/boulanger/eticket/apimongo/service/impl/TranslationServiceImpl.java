/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.service.impl;

import com.boulanger.eticket.apimongo.domain.mongo.Translation;
import com.boulanger.eticket.apimongo.repository.mongo.TranslationMongoRepository;
import com.boulanger.eticket.apimongo.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Translation Service implementation
 */
@Service("translationservice")
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

	private final TranslationMongoRepository translationMongoRepository;

	@Override
	public String getTranslatedLabelByIdAndLang(String id, String lang) {
		Translation translation = translationMongoRepository.findById(id).orElse(Translation.builder().id("Not found").lines(Collections.emptyList()).build());
		for (Translation.Line line : translation.getLines()) {
			if (line.getLang().equals(lang)) {
				return line.getLabel();
			}
		}
		return "Not found";
	}
}
