/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.repository.mongo;

import com.boulanger.eticket.apimongo.domain.mongo.WithdrawalTicket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * withdrawal mongo repository
 */

@Repository("ticketrepo2")

public interface WithdrawalTicketMongoRepository extends MongoRepository<WithdrawalTicket, String> {


    /**
     * Find a list of withdrawal ticke for a site id and line of products on this site
     *
     * @param saleId sale id
     * @param siteId user site
     * @param line   ticket
     * @return a list of {@link WithdrawalTicket}
     */
    @Query("{'$and':[ { 'saleId' : ?0 }, {'$and' : [ { 'siteId' : ?1 }, { 'line' : ?2 } ] } ] }")
    List<WithdrawalTicket> findBySaleAndSitedAndLine(final String saleId, final String siteId, final WithdrawalTicket.WithdrawalLine line);

    /**
     * Find a withdrawal ticket by this order's number
     * @param line order's number
     * @return a {@link WithdrawalTicket}
     */
    @Query("{ 'line' : ?0 }")
    WithdrawalTicket findByLine(final Integer line);


}