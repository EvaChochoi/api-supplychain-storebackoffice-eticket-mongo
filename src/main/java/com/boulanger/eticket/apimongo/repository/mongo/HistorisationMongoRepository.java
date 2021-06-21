/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.repository.mongo;

import com.boulanger.eticket.apimongo.domain.mongo.Historisation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Historisation Mongo Repository
 */
@Repository("historepository")
public interface HistorisationMongoRepository extends MongoRepository<Historisation, String> {
}
