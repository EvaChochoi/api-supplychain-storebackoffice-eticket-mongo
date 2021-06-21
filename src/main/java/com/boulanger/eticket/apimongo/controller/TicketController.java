/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.controller;

import com.boulanger.eticket.apimongo.domain.bff.Ticket;
import com.boulanger.eticket.apimongo.domain.kafkatomongo.TicketToStore;
import com.boulanger.eticket.apimongo.domain.mongo.Parameters;
import com.boulanger.eticket.apimongo.domain.mongo.StocksLabels;
import com.boulanger.eticket.apimongo.domain.mongo.StoredTicket;
import com.boulanger.eticket.apimongo.domain.mongo.WithdrawalTicket;
import com.boulanger.eticket.apimongo.mapper.TicketMapper;
import com.boulanger.eticket.apimongo.service.ParametersService;
import com.boulanger.eticket.apimongo.service.StocksLabelsService;
import com.boulanger.eticket.apimongo.service.TicketService;
import com.boulanger.platodin.common.error.ErrorResponse;
import com.boulanger.platodin.common.security.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.boulanger.eticket.apimongo.domain.Constants.Claims.EMPLOYEE_ID;
import static com.boulanger.eticket.apimongo.domain.Constants.Claims.FAMILY_NAME;
import static com.boulanger.eticket.apimongo.domain.Constants.Claims.GIVEN_NAME;

/**
 * Ticket Controller
 */
@Slf4j
@Tag(name = "TicketController", description = "Alimentation de la collection Ticket de la base MongoDB")
@RestController
@RequiredArgsConstructor
@Validated
@Component
public class TicketController {

	private final TicketService ticketService;
	private final TicketMapper ticketMapper;
	private final StocksLabelsService stocksLabelsService;
	private final ParametersService parametersService;

	/*----- Requête POST -----*/

	/**
	 * Create a Ticket in database
	 * @param ticketToStore a {@link TicketToStore}
	 * @return a copy of the {@link StoredTicket}
	 */
	@PostMapping("/ticket")
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	@Operation(
			method = "post",
			tags = "tickets",
			summary = "Create a new ticket",
			description = "Allow you to save a new ticket",
			operationId = "createTicket",
			responses = {
					@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = StoredTicket.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			}
	)
	public TicketToStore create(@RequestBody TicketToStore ticketToStore) {
			return ticketService.register(ticketToStore);
	}
	/*----- Requête POST -----*/

	/**
	 * Create a Ticket in database
	 * @param withdrawalTicket a {@link WithdrawalTicket}
	 * @return a copy of the {@link WithdrawalTicket}
	 */
	@PostMapping("/ticketWithdrawal")
	@ResponseBody
	//On ajoute une authorization
	@PreAuthorize("isAuthenticated()")
	//On indentifie les erreurs
	@Operation(
			method = "post",
			tags = "tickets",
			summary = "Create a new ticket withdrawal",
			description = "Allow you to save a new ticket",
			operationId = "createWithdrawalTicket",
			responses = {
					@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = StoredTicket.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			}
	)
	//Création de la méthode
	public List<StoredTicket> createWithdrawalTicket(@RequestBody WithdrawalTicket withdrawalTicket){

		/**
		 * Création d'une liste StoredTicket qui permettera de stocker les nouveaux éléments du mapping
		 *
		 */

		List<StoredTicket> storedTicketList = new ArrayList<>();
		for(WithdrawalTicket.WithdrawalLine withdrawalLine : withdrawalTicket.getLines()){
			StoredTicket storedTicket = ticketMapper.withdrawalToStoredTicket(withdrawalTicket, withdrawalLine);
			storedTicketList.add(storedTicket);
			ticketService.register(storedTicket);
		}
		return storedTicketList;
	}


	/*----- Requête GET-All for all status greater than 1-----*/

	/**
	 * Get a list of {@link Ticket}
	 * @param siteId the site ID
	 * @param lang the language of the user
	 * @return a list of {@link Ticket}
	 */
	@GetMapping("/site/{siteId}")
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	@Operation(
			method = "get",
			tags = "tickets",
			summary = "Fetch all tickets with status from 1 to 3 to an authenticated user",
			description = "Allow you to fetch all available tickets with the status 1, 2 and 3 for the ticket owner",
			operationId = "fetchTickets",
			responses = {
					@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Ticket.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			},
			parameters = {
					@Parameter(name = "siteId", in = ParameterIn.PATH, required = true, description = "Site ID of the user"),
					@Parameter(name = "lang", in = ParameterIn.QUERY, required = true, description = "Language")
			}
	)
	public List<Ticket> getTickets(@PathVariable(name = "siteId") String siteId, @RequestParam(value = "lang") String lang) {
		var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<StoredTicket> storedTickets = ticketService.getTicketsBySiteIdAndUserId(siteId, user.getClaims().get(EMPLOYEE_ID));
		List<Ticket> ticketList = new ArrayList<>();
		for (StoredTicket storedTicket : storedTickets) {
			var ticket = ticketMapper.storedTicketToTicket(storedTicket, lang);
			ticketList.add(ticket.toBuilder().hierarchy(ticketMapper.nomenclatureToHierarchy(ticket.getProduct().getExternalNodeId())).build());
		}
		return ticketList;
	}

	/*----- Requête GET -----*/

	/**
	 * Get a {@link Ticket}
	 * @param eventId the ticket event ID
	 * @param lang the language of the user
	 * @return a {@link Ticket}
	 */
	@GetMapping("/ticket/{eventId}")
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	@Operation(
			method = "get",
			tags = "tickets",
			summary = "Fetch selected ticket to as authenticated user",
			description = "Allow you to fetch a selected ticket",
			operationId = "fetchTicket",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Ticket.class))),
					@ApiResponse(responseCode = "400", description = "Invalid eventId supplied", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "500", description = "Internal Server Error (probably due to a bad request)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			},
			parameters = {
					@Parameter(name = "eventId", in = ParameterIn.PATH, required = true, description = "Event ID of the ticket"),
					@Parameter(name = "lang", in = ParameterIn.QUERY, required = true, description = "Language")
			}
	)
	public Ticket getTicket(@PathVariable(name = "eventId") String eventId, @RequestParam(value = "lang") String lang) {
		var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		var storedTicket = ticketService.getTicketByEventId(eventId);
		var quantityToPrepareInThisSite = ticketService
				.getQuantityBySiteIdAndSkuIdAndStatus(storedTicket.getSiteId(), storedTicket.getProduct().getSkuId(), 1);
		var storedTicketToUpdateWithStatus = storedTicket.toBuilder()
				.status(2)
				.ticketOwnerId(user.getClaims().get(EMPLOYEE_ID))
				.ticketOwner(user.getClaims().get(GIVEN_NAME) + " " + user.getClaims().get(FAMILY_NAME))
				.build();
		ticketService.save(storedTicketToUpdateWithStatus);
		var ticket = ticketMapper.storedTicketToTicket(storedTicket, lang);
		ticket = ticket.toBuilder()
				.product(ticket.getProduct().toBuilder().quantityToPrepareInThisSite(quantityToPrepareInThisSite).build())
				.hierarchy(ticketMapper.nomenclatureToHierarchy(ticket.getProduct().getExternalNodeId()))
				.build();
		return ticket;
	}

	/*----- Requête PUT -----*/

	/**
	 * Update the content of a ticket
	 * @param eventId the ticket event ID
	 * @param ticket the ticket to update
	 * @param lang the language of the user
	 * @return the updated ticket
	 */
	@PutMapping("/ticket/{eventId}")
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	@Operation(
			method = "put",
			tags = "tickets",
			summary = "Update selected ticket status",
			description = "Allow you to update the status of a selected ticket",
			operationId = "updateTicketStatus",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Ticket.class))),
					@ApiResponse(responseCode = "400", description = "Invalid ticketId supplied", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "500", description = "Internal Server Error (probably due to a bad request)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			},
			parameters = {
					@Parameter(name = "eventId", in = ParameterIn.PATH, required = true, description = "Event ID of the ticket"),
					@Parameter(name = "lang", in = ParameterIn.QUERY, required = true, description = "Language")
			}
	)
	public Ticket updateTicket(@PathVariable(name = "eventId") String eventId, @RequestBody Ticket ticket,
			@RequestParam(value = "lang") String lang) {
		if (ticket.getEventId().equals(eventId)) {
			return ticketMapper.storedTicketToTicket(ticketService.update(ticketMapper.ticketToStoredTicket(ticket)), lang);
		}
		return ticket;
	}

	/*----------Paramètres------------*/

	/*----- Requête GET Parameters -----*/

	/**
	 * Get the front parameters in the database
	 * @return the {@link Parameters}
	 */
	@GetMapping("/parameters")
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	@Operation(
			method = "get",
			tags = "tickets",
			summary = "Fetch the parameters",
			description = "Allow you to fetch the parameters for the Front eTicket",
			operationId = "fetchRefreshTime",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Ticket.class))),
					@ApiResponse(responseCode = "400", description = "Invalid eventId supplied", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "500", description = "Internal Server Error (probably due to a bad request)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			}
	)
	public Parameters getParameters() {
		return parametersService.getParameters();
	}

	/*----- Requête GET StocksLabels -----*/

	/**
	 * Get the Stocks Labels
	 * @return the {@link StocksLabels}
	 */
	@GetMapping("/stockslabels")
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	@Operation(
			method = "get",
			tags = "tickets",
			summary = "Fetch the labels of stocks",
			description = "Allow you to fetch the labels of stocks for the Front eTicket",
			operationId = "fetchStocksLabels",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Ticket.class))),
					@ApiResponse(responseCode = "400", description = "Invalid eventId supplied", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "500", description = "Internal Server Error (probably due to a bad request)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			}
	)
	public StocksLabels getStocksLabels() {
		return stocksLabelsService.getStocksLabels();
	}

}
