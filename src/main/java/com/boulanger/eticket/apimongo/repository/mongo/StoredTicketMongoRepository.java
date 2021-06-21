/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.repository.mongo;

import com.boulanger.eticket.apimongo.domain.mongo.StoredTicket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Stored Ticket Mongo Repository
 */
@Repository("ticketrepo")
public interface StoredTicketMongoRepository extends MongoRepository<StoredTicket, String> {

	/**
	 * Find a stored ticket by his event ID
	 * @param eventId event ID
	 * @return a {@link StoredTicket}
	 */
	@Query
	StoredTicket findByEventId(final String eventId);

	/**
	 * Find a list of stored ticket in this site for status = 1, 2, and for this user , status = 3
	 * @param siteId user site
	 * @param ticketOwnerId userId
	 * @return a list of {@link StoredTicket}
	 */
	@Query("{'$and':[ { 'siteId' : ?0 }, { '$or':[ { 'status' : {$lte: 2} }, { '$and' :[ { 'status' : 3 }, { 'ticketOwnerId' : ?1 } ] }] }] }")
	List<StoredTicket> findBySiteIdAndStatusAndAndTicketOwnerId(final String siteId, final String ticketOwnerId);

	/**
	 * Find a list of stored ticket for a product type and a ticket status on this site
	 * @param siteId user site
	 * @param skuId SKU ID
	 * @param status ticket status
	 * @return a list of {@link StoredTicket}
	 */
	@Query("{'$and':[ { 'siteId' : ?0 }, {'$and' : [ { 'product.skuId' : ?1 }, { 'status' : ?2 } ] } ] }")
	List<StoredTicket> findBySiteIdAndSkuIdAndStatus(final String siteId, final String skuId, final int status);

}
