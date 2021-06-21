  
/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.domain.mongo;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

/**
 * Ticket withdrawal
 */
@Document(collection = "ticket")
@Value
@Builder(toBuilder = true)
public class WithdrawalTicket {

	private Instant eventDate;
	private Boolean isFromDrive;
	private String siteId;
	private Integer driveTerminal;
	private List<LineWithdrawal> lines;
	private String saleId;
	private String customerId;
	private String customerName;
	private String customerFirstName;
	private String customerPhoneNumber;
	private LineWithdrawal lineWithdrawals;


	@Value
	@Builder
	public static class LineWithdrawal{
		private int lineId;
		private String productSapId;
		private int quantity;


	}

}