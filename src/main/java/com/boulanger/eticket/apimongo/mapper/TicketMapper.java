/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */
package com.boulanger.eticket.apimongo.mapper;

import com.boulanger.eticket.apimongo.domain.bff.Ticket;
import com.boulanger.eticket.apimongo.domain.catalog.CatalogProduct;
import com.boulanger.eticket.apimongo.domain.kafkatomongo.TicketToStore;
import com.boulanger.eticket.apimongo.domain.mongo.Nomenclature;
import com.boulanger.eticket.apimongo.domain.mongo.StoredTicket;
import com.boulanger.eticket.apimongo.domain.mongo.WithdrawalTicket;
import com.boulanger.eticket.apimongo.service.NomenclatureService;
import com.boulanger.eticket.apimongo.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

/**
 * Mapper class
 */
@Component
@RequiredArgsConstructor
public class TicketMapper {

	private final TranslationService translationService;
	private final NomenclatureService nomenclatureService;

	/**
	 * Mapping from stored ticket to ticket
	 * @param storedTicket {@link StoredTicket}
	 * @param lang the language of the user
	 * @return {@link Ticket}
	 */
	public Ticket storedTicketToTicket(StoredTicket storedTicket, String lang) {
		return Ticket.builder()
				.saleId(storedTicket.getSaleId())
				.line(storedTicket.getLine())
				.eventId(storedTicket.getEventId())
				.type(storedTicket.getType())
				.deliveryModeCode(storedTicket.getDeliveryModeCode())
				.deliveryModeLabel(translationService.getTranslatedLabelByIdAndLang(storedTicket.getDeliveryModeCode(), lang))
				.status(storedTicket.getStatus())
				.ticketOwnerId(storedTicket.getTicketOwnerId())
				.ticketOwner(storedTicket.getTicketOwner())
				.creationDate(storedTicket.getCreationDate())
				.notificationDate(Instant.parse(storedTicket.getNotificationDate()))
				.pickupDate(Instant.parse(storedTicket.getPickupDate()))
				.siteId(storedTicket.getSiteId())
				.storageNumber(storedTicket.getStorageNumber())
				.product(Ticket.Product.builder()
						.sapId(storedTicket.getProduct().getSapId())
						.skuId(storedTicket.getProduct().getSkuId())
						.eans(storedTicket.getProduct().getEans())
						.quantity(storedTicket.getProduct().getQuantity())
						.conditionId(storedTicket.getProduct().getConditionId())
						.photo(storedTicket.getProduct().getPhoto())
						.label(storedTicket.getProduct().getLabel())
						.externalNodeId(storedTicket.getProduct().getExternalNodeId())
						.brand(storedTicket.getProduct().getBrand())
						.build())
				.vendor(Ticket.Vendor.builder()
						.personalNumber(storedTicket.getVendor().getPersonalNumber())
						.name(storedTicket.getVendor().getName())
						.build())
				.customer(Ticket.Customer.builder()
						.id(storedTicket.getCustomer().getIdentification())
						.name(storedTicket.getCustomer().getName())
						.phone(storedTicket.getCustomer().getPhoneNumber())
						.build())
				.feedback(Ticket.Feedback.builder()
						.result(storedTicket.getFeedback().getResult())
						.reason(storedTicket.getFeedback().getReason())
						.quantity(storedTicket.getFeedback().getQuantity())
						.build())
				.build();
	}

	/**
	 * Mapping from ticket to stored ticket
	 * @param ticket {@link Ticket}
	 * @return {@link StoredTicket}
	 */
	public StoredTicket ticketToStoredTicket(Ticket ticket) {

		return StoredTicket.builder()
				.id(ticket.getEventId())
				.saleId(ticket.getSaleId())
				.line(ticket.getLine())
				.eventId(ticket.getEventId())
				.type(ticket.getType())
				.deliveryModeCode(ticket.getDeliveryModeCode())
				.status(ticket.getStatus())
				.ticketOwnerId(ticket.getTicketOwnerId())
				.ticketOwner(ticket.getTicketOwner())
				.creationDate(ticket.getCreationDate())
				.notificationDate(ticket.getNotificationDate().toString())
				.pickupDate(ticket.getPickupDate().toString())
				.siteId(ticket.getSiteId())
				.storageNumber(ticket.getStorageNumber())
				.product(StoredTicket.Product.builder()
						.sapId(ticket.getProduct().getSapId())
						.skuId(ticket.getProduct().getSkuId())
						.eans(ticket.getProduct().getEans())
						.quantity(ticket.getProduct().getQuantity())
						.conditionId(ticket.getProduct().getConditionId())
						.photo(ticket.getProduct().getPhoto())
						.label(ticket.getProduct().getLabel())
						.externalNodeId(ticket.getProduct().getExternalNodeId())
						.brand(ticket.getProduct().getBrand())
						.build())
				.vendor(storedVendorProcess(ticket)
						.build())
				.customer(StoredTicket.Customer.builder()
						.identification(ticket.getCustomer().getId())
						.name(ticket.getCustomer().getName())
						.phoneNumber(ticket.getCustomer().getPhone())
						.build())
				.feedback(storedFeedbackProcess(ticket)
						.build())
				.build();

	}

	/**
	 * Mapping from a ticket to store to a stored ticket
	 * @param ticketToStore {@link TicketToStore}
	 * @return {@link StoredTicket}
	 */
	public StoredTicket ticketToStoreToStoredTicket(TicketToStore ticketToStore) {

		return StoredTicket.builder()
				.id(ticketToStore.getEventId())
				.saleId(ticketToStore.getSaleId())
				.line(ticketToStore.getLine())
				.eventId(ticketToStore.getEventId())
				.type(ticketToStore.getType())
				.deliveryModeCode(ticketToStore.getDeliveryModeCode())
				.status(ticketToStore.getStatus())
				.ticketOwnerId(ticketToStore.getTicketOwnerId())
				.ticketOwner(ticketToStore.getTicketOwner())
				.creationDate(ticketToStore.getCreationDate())
				.notificationDate(ticketToStore.getNotificationDate())
				.pickupDate(ticketToStore.getPickupDate())
				.siteId(ticketToStore.getSiteId())
				.storageNumber(ticketToStore.getStorageNumber())
				.product(StoredTicket.Product.builder()
						.sapId(ticketToStore.getProduct().getSapId())
						.skuId(ticketToStore.getProduct().getSkuId())
						.eans(ticketToStore.getProduct().getEans())
						.quantity(ticketToStore.getProduct().getQuantity())
						.conditionId(ticketToStore.getProduct().getConditionId())
						.photo(ticketToStore.getProduct().getPhoto())
						.label(ticketToStore.getProduct().getLabel())
						.externalNodeId(ticketToStore.getProduct().getExternalNodeId())
						.brand(ticketToStore.getProduct().getBrand())
						.build())
				.vendor(StoredTicket.Vendor.builder()
						.personalNumber(ticketToStore.getVendor().getPersonalNumber())
						.name(ticketToStore.getVendor().getName())
						.build())
				.customer(StoredTicket.Customer.builder()
						.identification(ticketToStore.getCustomer().getId())
						.name(ticketToStore.getCustomer().getName())
						.phoneNumber(ticketToStore.getCustomer().getPhoneNumber())
						.build())
				.feedback(StoredTicket.Feedback.builder()
						.result(ticketToStore.getFeedback().getResult())
						.reason(ticketToStore.getFeedback().getReason())
						.quantity(ticketToStore.getFeedback().getQuantity())
						.build())
				.build();

	}

	/**
	 * Mapping from an externalNodeId to a hierarchy
	 * @param externalNodeId externalNodeId
	 * @return a {@link com.boulanger.eticket.apimongo.domain.bff.Ticket.Hierarchy}
	 */
	public Ticket.Hierarchy nomenclatureToHierarchy(String externalNodeId) {
		List<Nomenclature> nomenclatures = nomenclatureService.getNomenclatures(externalNodeId);
		return Ticket.Hierarchy.builder()
				.productLine(Ticket.Hierarchy.Niveau.builder()
						.code(nomenclatures.get(0).getExternalNodeId())
						.label(nomenclatures.get(0).getLabel().getFr_FR())
						.build())
				.aisle(Ticket.Hierarchy.Niveau.builder()
						.code(nomenclatures.get(1).getExternalNodeId())
						.label(nomenclatures.get(1).getLabel().getFr_FR())
						.build())
				.family(Ticket.Hierarchy.Niveau.builder()
						.code(nomenclatures.get(2).getExternalNodeId())
						.label(nomenclatures.get(2).getLabel().getFr_FR())
						.build())
				.build();
	}

	/**
	 * Stored Vendor builder mapping. To use in case of vendor == null
	 * @param ticket {@link Ticket}
	 * @return a vendorBuilder
	 */
	public StoredTicket.Vendor.VendorBuilder storedVendorProcess (Ticket ticket) {
		StoredTicket.Vendor.VendorBuilder vendorBuilder = StoredTicket.Vendor.builder();
		if (ticket.getVendor() != null) {
			if (ticket.getVendor().getName() != null) {
				vendorBuilder.name(ticket.getVendor().getName());
			}
			if (ticket.getVendor().getPersonalNumber() != null) {
				vendorBuilder.personalNumber(ticket.getVendor().getPersonalNumber());
			}
		}
		return vendorBuilder;
	}

	/**
	 * Stored Feedback builder mapping. To use in case of feedback == null
	 * @param ticket {@link Ticket}
	 * @return a feedbackBuilder
	 */
	public StoredTicket.Feedback.FeedbackBuilder storedFeedbackProcess (Ticket ticket) {
		StoredTicket.Feedback.FeedbackBuilder feedbackBuilder = StoredTicket.Feedback.builder();
		if (ticket.getFeedback() != null){
			if (ticket.getFeedback().getResult() != null){
				feedbackBuilder.result(ticket.getFeedback().getResult());
			}
			if (ticket.getFeedback().getQuantity() > 0) {
				feedbackBuilder.quantity(ticket.getFeedback().getQuantity());
			}
			if (ticket.getFeedback().getReason() != null) {
				feedbackBuilder.reason(ticket.getFeedback().getReason());
			}
		}
		return feedbackBuilder;
	}



	/**
	 * Mapping from withdrawal to ticket
	 *
	 * @param withdrawalTicket {@link WithdrawalTicket}
	 * @param lang             the language of the user
	 * @return {@link Ticket}
	 */
	public Ticket withdrawalToTicket(WithdrawalTicket withdrawalTicket, String lang) {
		return Ticket.builder()
				.eventDate(withdrawalTicket.getEventDate())
				.isFromDrive(withdrawalTicket.getIsFromDrive())
				.siteId(withdrawalTicket.getSiteId())
				.driveTerminal(withdrawalTicket.getDriveTerminal())
				.lineTickets((Ticket.LineTicket) Ticket.LineTicket.builder()
						.line(withdrawalTicket.getLineWithdrawals().getLineId())
						.productSapId(withdrawalTicket.getLineWithdrawals().getProductSapId())
						.quantity(withdrawalTicket.getLineWithdrawals().getQuantity())
						.build())
				.saleId(withdrawalTicket.getSaleId())
				.customerId(withdrawalTicket.getCustomerId())
				.customerName(withdrawalTicket.getCustomerName())
				.customerFirstName(withdrawalTicket.getCustomerFirstName())
				.customerPhoneNumber(withdrawalTicket.getCustomerPhoneNumber())
				.build();
	}

	/**
	 * Mapping from ticket to withdrawal
	 *
	 * @param ticket {@link Ticket}
	 * @return {@link WithdrawalTicket}
	 */
	public WithdrawalTicket ticketToWithdrawal(Ticket ticket) {
		return WithdrawalTicket.builder()
				.eventDate(ticket.getEventDate())
				.isFromDrive(ticket.getIsFromDrive())
				.siteId(ticket.getSiteId())
				.driveTerminal(ticket.getDriveTerminal())
				.lineWithdrawals((WithdrawalTicket.LineWithdrawal) WithdrawalTicket.LineWithdrawal.builder()
						.lineId(ticket.getLineTickets().getLine())
						.productSapId(ticket.getLineTickets().getProductSapId())
						.quantity(ticket.getLineTickets().getQuantity())
						.build())
				.saleId(ticket.getSaleId())
				.customerId(ticket.getCustomerId())
				.customerName(ticket.getCustomerName())
				.customerFirstName(ticket.getCustomerFirstName())
				.customerPhoneNumber(ticket.getCustomerPhoneNumber())
				.build();

	}


	/**
	 * Mapping from withdrawal to storedTicket
	 *
	 * @param withdrawalTicket {@link WithdrawalTicket}
	 * @return {@link StoredTicket}
	 */

	public StoredTicket withdrawalToStoredTicket(WithdrawalTicket withdrawalTicket, WithdrawalTicket.LineWithdrawal lineWithdrawal) {
		return StoredTicket.builder()
				.creationDate(withdrawalTicket.getEventDate())
				.isFromDrive(withdrawalTicket.getIsFromDrive())
				.siteId(withdrawalTicket.getSiteId())
				.driveTerminal(withdrawalTicket.getDriveTerminal())
				.saleId(withdrawalTicket.getSaleId())
				.line(withdrawalTicket.getLineWithdrawals().getLineId())
				.eventId(withdrawalTicket.getSaleId()+"-"+lineWithdrawal.getLineId()+"-W")
				.id(withdrawalTicket.getSaleId()+"-"+lineWithdrawal.getLineId()+"-W")
				.customer((StoredTicket.Customer) StoredTicket.Customer.builder()
						.identification(withdrawalTicket.getCustomerId())
						.name(withdrawalTicket.getCustomerName())
						.firstName(withdrawalTicket.getCustomerFirstName())
						.phoneNumber(withdrawalTicket.getCustomerPhoneNumber())
						.build())
				.build();

	}

	public StoredTicket catalogToStoredTicket(CatalogProduct catalogProduct) {
		return StoredTicket.builder()
				.product((StoredTicket.Product) StoredTicket.Product.builder()
						.sapId(catalogProduct.getProduct().getProductSapId())
						.skuId(catalogProduct.getProduct().getProductSkuIds())
						.quantity(catalogProduct.getProduct().getQuantity())
						.build())
				.build();
	}

}
