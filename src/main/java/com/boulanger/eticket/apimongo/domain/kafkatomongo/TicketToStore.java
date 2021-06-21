/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.domain.kafkatomongo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;

/**
 * Ticket class for creation
 */
@Value
@Builder
@With
public class TicketToStore {

	private String saleId;
	private int line;
	private String eventId;
	private String type;
	private String deliveryModeCode;
	private int status;
	@Schema(required = false, description = "precise if this ticket is used as an update")
	private boolean updated;
	private String ticketOwnerId;
	private String ticketOwner;
	private Instant creationDate;
	private String notificationDate;
	private String pickupDate;
	private String siteId;
	private TicketToStore.Product product;
	private TicketToStore.Vendor vendor;
	private TicketToStore.Customer customer;
	private String storageNumber;
	private TicketToStore.Feedback feedback;

	/**
	 * Product from TicketToStore
	 */
	@Value
	@Builder
	public static class Product {

		private String sapId;
		private String skuId;
		private List<String> eans;
		private int quantity;
		private String conditionId;
		private String photo;
		private String label;
		private String externalNodeId;
		@Schema(required = false)
		private String brand;

	}

	/**
	 * Vendor from TicketToStore
	 */
	@Value
	@Builder
	public static class Vendor {

		private String personalNumber;
		private String name;
	}

	/**
	 * Customer from TicketToStore
	 */
	@Value
	@Builder
	public static class Customer {

		private String id;
		private String name;
		private String phoneNumber;

	}

	/**
	 * Feedback from TicketToStore
	 */
	@Value
	@Builder
	public static class Feedback {

		private String result;
		private String reason;
		private int quantity;
	}
}
