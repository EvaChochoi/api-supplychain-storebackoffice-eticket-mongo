/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.repository.mongo;

import com.boulanger.eticket.apimongo.domain.mongo.Nomenclature;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Nomenclature Mongo Repository
 */
@Repository("nomenclaturerepo")
public interface NomenclatureMongoRepository extends MongoRepository<Nomenclature, String> {

	/**
	 * Find the nomenclature in database with this externalNodeId
	 * @param externalNodeId externalNodeId field
	 * @param type type field
	 * @param salesOrganization salesOrganization field
	 * @return a Nomenclature
	 */
	@Query("{ $and : [{ 'externalNodeId' : ?0 }, { 'type' : ?1 }, { 'salesOrganization' : ?2 }] }")
	Nomenclature findByExternalNodeId(final String externalNodeId, final String type, final String salesOrganization);


}
