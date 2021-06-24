/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.service.impl;

import com.boulanger.eticket.apimongo.domain.Constants;
import com.boulanger.eticket.apimongo.domain.kafkatomongo.TicketToStore;
import com.boulanger.eticket.apimongo.domain.mongo.Historisation;
import com.boulanger.eticket.apimongo.domain.mongo.StoredTicket;
import com.boulanger.eticket.apimongo.domain.mongo.WithdrawalTicket;
import com.boulanger.eticket.apimongo.mapper.TicketMapper;
import com.boulanger.eticket.apimongo.repository.mongo.StoredTicketMongoRepository;
import com.boulanger.eticket.apimongo.service.HistorisationService;
import com.boulanger.eticket.apimongo.service.TicketService;
import com.boulanger.platodin.web.exception.technical.TechnicalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Ticket Service implementation
 */
@Slf4j
@Service("ticketservice")
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

	private final StoredTicketMongoRepository storedTicketMongoRepository;
	private final HistorisationService historisationService;
	private final TicketMapper ticketMapper;

	@Override public TicketToStore register(TicketToStore ticketToStore) {
		var storedTicket = storedTicketMongoRepository.findByEventId(ticketToStore.getEventId());

		if (storedTicket == null) {
			var ticketToInsert = ticketMapper.ticketToStoreToStoredTicket(ticketToStore);
			storedTicketMongoRepository.insert(ticketToInsert);
			return ticketToStore;
		}

		if (storedTicket.getStatus() != 1) {
			throw new TechnicalException("Cannot insert this ticket due to a duplicate key", "ETKT_001");
		}

		log.warn("Cannot insert duplicated ticket {} but update the quantity to prepare", ticketToStore.getEventId());

		var updatedProduct = storedTicket.getProduct().withQuantity(ticketToStore.getProduct().getQuantity());
		var updatedTicket = storedTicket.withProduct(updatedProduct);
		storedTicketMongoRepository.save(updatedTicket);

		return ticketToStore.withUpdated(true);
	}

	@Override public StoredTicket save(StoredTicket storedTicket) {
		return storedTicketMongoRepository.save(storedTicket);
	}

	@Override public List<StoredTicket> getTicketsBySiteIdAndUserId(String siteId, String userId) {
		return storedTicketMongoRepository.findBySiteIdAndStatusAndAndTicketOwnerId(siteId, userId);
	}

	@Override public int getQuantityBySiteIdAndSkuIdAndStatus(String siteId, String skuId, int status) {
		List<StoredTicket> storedTickets = storedTicketMongoRepository.findBySiteIdAndSkuIdAndStatus(siteId, skuId, status);
		var quantity = 0;
		for (StoredTicket storedTicket : storedTickets) {
			quantity = quantity + storedTicket.getProduct().getQuantity();
		}
		return quantity;
	}

	@Override public StoredTicket update(StoredTicket storedTicket) {
		var historisationBuilder = Historisation.builder()
				.storedTicket(storedTicket)
				.date(Instant.now());
		switch (storedTicket.getStatus()) {
			case 1:
				historisationBuilder.type(Constants.TicketStatus.LIBERATION);
				break;
			case 2:
				historisationBuilder.type(Constants.TicketStatus.PRIS_EN_COMPTE);
				break;
			case 3:
				historisationBuilder.type(Constants.TicketStatus.ACQUITTEMENT_OK);
				break;
			case 4:
				historisationBuilder.type(Constants.TicketStatus.ACQUITTEMENT_KO);
				break;
			case 6:
				historisationBuilder.type(Constants.TicketStatus.TERMINE);
				break;
			default:
				historisationBuilder.type("inconnu");
				break;

		}
		var historisation =  historisationBuilder.build();
		historisationService.register(historisation);
		return storedTicketMongoRepository.save(storedTicket);
	}

	@Override public StoredTicket getTicketByEventId(String eventId) {
		return storedTicketMongoRepository.findByEventId(eventId);
	}

	@Override
	public StoredTicket register(StoredTicket storedTicket){
		return  storedTicketMongoRepository.save(storedTicket);
	}

}
