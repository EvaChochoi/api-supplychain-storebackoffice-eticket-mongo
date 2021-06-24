/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.domain.mongo;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

/**
 * Stored Ticket class
 */
@Document(collection = "ticket")
@Value
@Builder(toBuilder = true)
public class StoredTicket {

	@Id
	private String id;
	private String saleId;
	private int line;
	private String eventId; //line
	private String type;
	private String deliveryModeCode;
	private int status;
	private String ticketOwnerId;
	private String ticketOwner;
	private Instant creationDate;
	private String notificationDate;
	private String pickupDate;
	private String siteId;
	private Boolean isFromDrive;
	private Boolean isWithdrawalTicket;
	private Integer driveTerminal;
	private String sapId;
	private int quantity;
	@With
	private Product product;
	private Vendor vendor;
	private Customer customer;
	private String storageNumber;
	private Feedback feedback;

	/**
	 * Product from StoredTicket
	 */
	@Value
	@Builder
	@With
	public static class Product {

		private String sapId;
		private String skuId;
		private List<String> eans;
		private int quantity;
		private String conditionId;
		private String photo;
		private String label;
		private String externalNodeId;
		private String brand;

	}

	/**
	 * Vendor from StoredTicket
	 */
	@Value
	@Builder
	public static class Vendor {

		private String personalNumber;
		private String name;
	}

	/**
	 * Customer from StoredTicket
	 */
	@Value
	@Builder
	public static class Customer {

		private String identification;
		private String lastName;
		private String firstName;
		private String phoneNumber;

	}

	/**
	 * Feedback from StoredTicket
	 */
	@Value
	@Builder
	public static class Feedback {

		private String result;
		private String reason;
		private int quantity;
	}
}
