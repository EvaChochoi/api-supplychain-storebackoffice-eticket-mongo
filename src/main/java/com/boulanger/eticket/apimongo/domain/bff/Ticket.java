/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.domain.bff;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;

/**
 * Ticket class
 */
@Value
@Builder(toBuilder = true)
public class Ticket {

	private String saleId;
	private int line;
	private String eventId;
	private String type;
	private String deliveryModeCode;
	private String deliveryModeLabel;
	private int status;
	private String ticketOwnerId;
	private String ticketOwner;
	private Instant creationDate;
	private Instant notificationDate;
	private Instant pickupDate;
	private String siteId;
	private Product product;
	private Vendor vendor;
	private Hierarchy hierarchy;
	private Customer customer;
	private String storageNumber;
	private Feedback feedback;
	private Instant eventDate;
	private Boolean isFromDrive;
	private int driveTerminal;
	private List<LineTicket> lines;
	private String customerId;
	private String customerName;
	private String customerFirstName;
	private String customerPhoneNumber;
	private LineTicket lineTickets;


	/**
	 * Product from Ticket
	 */
	@Value
	@Builder(toBuilder = true)
	public static class Product {

		private String sapId;
		private String skuId;
		private List<String> eans;
		private int quantity;
		private int quantityToPrepareInThisSite;
		private String conditionId;
		private String photo;
		private String label;
		private String externalNodeId;
		@Schema(required = false)
		private String brand;

	}

	/**
	 * Vendor from Ticket
	 */
	@Value
	@Builder
	public static class Vendor {

		private String personalNumber;
		private String name;
	}

	/**
	 * Hierarchy from Ticket
	 */
	@Value
	@Builder
	public static class Hierarchy {

		private Niveau productLine;
		private Niveau aisle;
		private Niveau family;

		/**
		 * Hierarchy Niveau from Ticket
		 */
		@Value
		@Builder
		public static class Niveau {
			private String code;
			private String label;
		}

	}

	/**
	 * Customer from Ticket
	 */
	@Value
	@Builder
	public static class Customer {

		private String id;
		private String name;
		private String phone;

	}

	/**
	 * Feedback from Ticket
	 */
	@Value
	@Builder
	public static class Feedback {

		private String result;
		private String reason;
		private int quantity;
	}

	@Value
	@Builder
	public static class LineTicket{
		private int line;
		private String productSapId;
		private int quantity;


	}
}
