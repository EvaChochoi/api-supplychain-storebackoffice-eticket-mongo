/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.repository.mongo;

import com.boulanger.eticket.apimongo.domain.mongo.Translation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Translation Mongo Repository
 */
@Repository
public interface TranslationMongoRepository extends MongoRepository<Translation, String> {

	//	À implémenter dans prochaine version
	//	@Query ("{ '$and' :[{ '_id' : ?0 }, { 'lines.lang' : ?1 }] }, { 'lines.label' : 1, '_id' : 0 }")
	//	String findLabelByIdAndLang(final String id, final String lang);

}
