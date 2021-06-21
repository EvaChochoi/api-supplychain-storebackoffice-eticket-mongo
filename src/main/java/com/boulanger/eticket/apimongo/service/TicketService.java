/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.service;

import com.boulanger.eticket.apimongo.domain.kafkatomongo.TicketToStore;
import com.boulanger.eticket.apimongo.domain.mongo.StoredTicket;
import com.boulanger.eticket.apimongo.domain.mongo.WithdrawalTicket;

import java.util.List;

/**
 * Ticket Services
 */
public interface TicketService {

	/**
	 * create a {@link StoredTicket} in the database
	 * @param ticketToStore {@link TicketToStore}
	 * @return the inserted ticket
	 */
	TicketToStore register(TicketToStore ticketToStore);

	/**
	 * create or update a {@link StoredTicket} in the database
	 * @param storedTicket {@link StoredTicket}
	 * @return the saved stored ticket
	 */
	StoredTicket save(StoredTicket storedTicket);

	/**
	 * Get a ticket list with a site ID and a user ID
	 * @param siteId site ID
	 * @param userId user ID
	 * @return a list of {@link StoredTicket}
	 */
	List<StoredTicket> getTicketsBySiteIdAndUserId(String siteId, String userId);

	/**
	 * Get the quantity of a product to prepare on this site
	 * @param siteId site ID
	 * @param skuId SKU ID
	 * @param status ticket status
	 * @return the quantity of product to prepare
	 */
	int getQuantityBySiteIdAndSkuIdAndStatus(String siteId, String skuId, int status);

	/**
	 * Update a ticket
	 * @param storedTicket the ticket
	 * @return a copy of the updated ticket
	 */
	StoredTicket update(StoredTicket storedTicket);

	/**
	 * Get a ticket with an event ID
	 * @param eventId event ID
	 * @return a {@link StoredTicket}
	 */
	StoredTicket getTicketByEventId(String eventId);



	/**
	 * create a {@link com.boulanger.eticket.apimongo.domain.mongo.StoredTicket} in the database
	 *
	 * @param storedTicket {@link StoredTicket}
	 * @return the saved withdrawal
	 */
	StoredTicket register(StoredTicket storedTicket);




}
