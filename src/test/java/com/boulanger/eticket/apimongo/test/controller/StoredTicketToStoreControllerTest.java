/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.test.controller;

import com.boulanger.eticket.apimongo.controller.TicketController;
import com.boulanger.eticket.apimongo.domain.bff.Ticket;
import com.boulanger.eticket.apimongo.domain.kafkatomongo.TicketToStore;
import com.boulanger.eticket.apimongo.domain.mongo.*;
import com.boulanger.eticket.apimongo.mapper.TicketMapper;
import com.boulanger.eticket.apimongo.repository.mongo.*;
import com.boulanger.eticket.apimongo.service.TicketService;
import com.boulanger.platodin.test.TestConfiguration;
import com.boulanger.platodin.test.TestUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@SpringBootTest(classes = {JacksonAutoConfiguration.class, TestConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoredTicketToStoreControllerTest {

    private final MockMvc mockMvc;

    private final StoredTicketMongoRepository storedTicketMongoRepository;
    private final ParametersMongoRepository parametersMongoRepository;
    private final NomenclatureMongoRepository nomenclatureMongoRepository;
    private final TranslationMongoRepository translationMongoRepository;
    private final StocksLabelsMongoRepository stocksLabelsMongoRepository;
    private final TicketMapper ticketMapper;

    private ObjectMapper objectMapper;

    public static final String HEADER_SUB = "X-Auth-sub";
    public static final String UID = "001";
    public static final String HEADER_TOKEN = "X-Auth-token";
    public static final String HEADER_SCOPES = "X-Auth-scopes";
    public static final String HEADER_CLAIMS_SITEID = "X-Auth-site_id";
    public static final String HEADER_CLAIMS_EMPLOYEE_ID = "X-Auth-employee_id";
    public static final String HEADER_CLAIMS_FAMILY_NAME = "X-Auth-family_name";
    public static final String HEADER_CLAIMS_GIVEN_NAME = "X-Auth-given_name";

    public static final String token = "DFDSQFGTEB.3E4FF2FDFS.856HYTH65YHY";
    public static final String siteId = "F224";
    public static final String scope = "bl:app.eticket:user";
    public static final String userId = "1234";
    public static final String familyName = "TEST";
    public static final String givenName = "Jean";


    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper().findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

        List<StoredTicket> storedTickets = TestUtils
                .loadTestResourceList(objectMapper, "/seeds/mongo/storedTicketList.json", StoredTicket.class, StoredTicketToStoreControllerTest.class);
        storedTicketMongoRepository.saveAll(storedTickets);

        List<Nomenclature> nomenclatures = TestUtils
                .loadTestResourceList(objectMapper, "/seeds/mongo/nomenclature.json", Nomenclature.class, StoredTicketToStoreControllerTest.class);
        nomenclatureMongoRepository.saveAll(nomenclatures);

        Parameters parameters = TestUtils
                .loadTestResource(objectMapper, "/seeds/mongo/parameters.json", Parameters.class, StoredTicketToStoreControllerTest.class);
        parametersMongoRepository.save(parameters);

        List<Translation> translations = TestUtils
                .loadTestResourceList(objectMapper, "/seeds/mongo/translation.json", Translation.class, StoredTicketToStoreControllerTest.class);
        translationMongoRepository.saveAll(translations);

        StocksLabels stocksLabels = TestUtils
                .loadTestResource(objectMapper, "/seeds/mongo/stocks-labels.json", StocksLabels.class, StoredTicketToStoreControllerTest.class);
        stocksLabelsMongoRepository.save(stocksLabels);

    }

    @Test
    void shouldGetTickets() throws Exception {

        this.mockMvc.perform(get("/site/F224?lang=fr_FR"))
                .andDo(print())
                .andExpect(status().isForbidden());

        this.mockMvc.perform(get("/site/F224?lang=fr_FR")
                .header(HEADER_SUB, UID)
                .header(HEADER_SCOPES, scope)
                .header(HEADER_TOKEN, token)
                .header(HEADER_CLAIMS_SITEID, siteId)
                .header(HEADER_CLAIMS_FAMILY_NAME, familyName)
                .header(HEADER_CLAIMS_GIVEN_NAME, givenName)
                .header(HEADER_CLAIMS_EMPLOYEE_ID, userId))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isNotEmpty());

    }

    @Test
    void shouldGetATicket() throws Exception {

        this.mockMvc.perform(get("/ticket/F905010453-1?lang=fr_FR"))
                .andDo(print())
                .andExpect(status().isForbidden());

        this.mockMvc.perform(get("/ticket/F905010453-1?lang=fr_FR")
                .header(HEADER_SUB, UID)
                .header(HEADER_SCOPES, scope)
                .header(HEADER_TOKEN, token)
                .header(HEADER_CLAIMS_SITEID, siteId)
                .header(HEADER_CLAIMS_FAMILY_NAME, familyName)
                .header(HEADER_CLAIMS_GIVEN_NAME, givenName)
                .header(HEADER_CLAIMS_EMPLOYEE_ID, userId))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.status").value(1))
                .andExpect(jsonPath("$.deliveryModeCode").value("REM"))
                .andExpect(jsonPath("$.deliveryModeLabel").value("Remise en magasin"));

        StoredTicket receivedTicket1 = storedTicketMongoRepository.findByEventId("F905010453-1");
        assertThat(receivedTicket1).isNotNull();
        assertThat(receivedTicket1.getStatus()).isEqualTo(2);

        this.mockMvc.perform(get("/ticket/F905010457-1?lang=en_US")
                .header(HEADER_SUB, UID)
                .header(HEADER_SCOPES, scope)
                .header(HEADER_TOKEN, token)
                .header(HEADER_CLAIMS_SITEID, siteId)
                .header(HEADER_CLAIMS_FAMILY_NAME, familyName)
                .header(HEADER_CLAIMS_GIVEN_NAME, givenName)
                .header(HEADER_CLAIMS_EMPLOYEE_ID, userId))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.status").value(1))
                .andExpect(jsonPath("$.deliveryModeCode").value("DRV"))
                .andExpect(jsonPath("$.deliveryModeLabel").value("Drive order"));

        StoredTicket receivedTicket2 = storedTicketMongoRepository.findByEventId("F905010457-1");
        assertThat(receivedTicket2).isNotNull();
        assertThat(receivedTicket2.getStatus()).isEqualTo(2);

    }

    @Test
    void shouldUpdateATicket() throws Exception {

        Ticket ticketToUpdate = TestUtils
                .loadTestResource(objectMapper, "/seeds/mongo/frontTicketToUpdate.json", Ticket.class, StoredTicketToStoreControllerTest.class);

        this.mockMvc.perform(put("/ticket/F905010453-3?lang=fr_FR")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketToUpdate)))
                .andDo(print())
                .andExpect(status().isForbidden());

        this.mockMvc.perform(put("/ticket/F905010453-3?lang=fr_FR")
                .header(HEADER_SUB, UID)
                .header(HEADER_SCOPES, scope)
                .header(HEADER_TOKEN, token)
                .header(HEADER_CLAIMS_SITEID, siteId)
                .header(HEADER_CLAIMS_FAMILY_NAME, familyName)
                .header(HEADER_CLAIMS_GIVEN_NAME, givenName)
                .header(HEADER_CLAIMS_EMPLOYEE_ID, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketToUpdate)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isNotEmpty());

        log.info(storedTicketMongoRepository.findAll().toString());
        StoredTicket updatedTicket = storedTicketMongoRepository.findByEventId("F905010453-3");
        assertThat(updatedTicket).isNotNull();
        assertThat(updatedTicket.getStatus()).isEqualTo(6);

    }

    @Test
    void shouldSaveATicket() throws Exception {
        TicketToStore ticketToSave = TestUtils
                .loadTestResource(objectMapper, "/seeds/mongo/ticketToSave.json", TicketToStore.class, StoredTicketToStoreControllerTest.class);

        this.mockMvc.perform(post("/ticket/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketToSave)))
                .andDo(print())
                .andExpect(status().isForbidden());

        this.mockMvc.perform(post("/ticket/")
                .header(HEADER_SUB, UID)
                .header(HEADER_SCOPES, scope)
                .header(HEADER_TOKEN, token)
                .header(HEADER_CLAIMS_SITEID, siteId)
                .header(HEADER_CLAIMS_FAMILY_NAME, familyName)
                .header(HEADER_CLAIMS_GIVEN_NAME, givenName)
                .header(HEADER_CLAIMS_EMPLOYEE_ID, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketToSave)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isNotEmpty());

        StoredTicket updatedTicket = storedTicketMongoRepository.findByEventId("F905010454-1");
        assertThat(updatedTicket).isNotNull();
        assertThat(updatedTicket.getStatus()).isEqualTo(1);
        assertThat(updatedTicket.getSaleId()).isEqualTo("F905010454");

    }

    @Test
    void shouldNotSaveATicket() throws Exception {
        TicketToStore ticketToSave = TestUtils
                .loadTestResource(objectMapper, "/seeds/mongo/ticketToNotUpdate.json", TicketToStore.class, StoredTicketToStoreControllerTest.class);

        StoredTicket ticketWithStatus2 = ticketMapper.ticketToStoreToStoredTicket(ticketToSave).toBuilder().status(2).build();
        storedTicketMongoRepository.insert(ticketWithStatus2);

        this.mockMvc.perform(post("/ticket/")
                .header(HEADER_SUB, UID)
                .header(HEADER_SCOPES, scope)
                .header(HEADER_TOKEN, token)
                .header(HEADER_CLAIMS_SITEID, siteId)
                .header(HEADER_CLAIMS_FAMILY_NAME, familyName)
                .header(HEADER_CLAIMS_GIVEN_NAME, givenName)
                .header(HEADER_CLAIMS_EMPLOYEE_ID, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketToSave)))
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }

    @Test
    void shouldSaveADuplicatedTicket() throws Exception {
        var ticketToSave1 = TestUtils
                .loadTestResource(objectMapper, "/seeds/mongo/ticketToSave.json", TicketToStore.class, StoredTicketToStoreControllerTest.class);
        var ticketToSave2 = TestUtils
                .loadTestResource(objectMapper, "/seeds/mongo/duplicatedTicketToSave.json", TicketToStore.class, StoredTicketToStoreControllerTest.class);
        storedTicketMongoRepository.save(ticketMapper.ticketToStoreToStoredTicket(ticketToSave1));

        this.mockMvc.perform(post("/ticket/")
                .header(HEADER_SUB, UID)
                .header(HEADER_SCOPES, scope)
                .header(HEADER_TOKEN, token)
                .header(HEADER_CLAIMS_SITEID, siteId)
                .header(HEADER_CLAIMS_FAMILY_NAME, familyName)
                .header(HEADER_CLAIMS_GIVEN_NAME, givenName)
                .header(HEADER_CLAIMS_EMPLOYEE_ID, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketToSave2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updated", is(true)));

        var storedTicket = storedTicketMongoRepository.findByEventId(ticketToSave1.getEventId());
        assertThat(storedTicket.getProduct().getQuantity()).isEqualTo(3);

    }

    @Test
    void shouldGetParameters() throws Exception {

        this.mockMvc.perform(get("/parameters"))
                .andDo(print())
                .andExpect(status().isForbidden());

        this.mockMvc.perform(get("/parameters")
                .header(HEADER_SUB, UID)
                .header(HEADER_SCOPES, scope)
                .header(HEADER_TOKEN, token)
                .header(HEADER_CLAIMS_SITEID, siteId)
                .header(HEADER_CLAIMS_FAMILY_NAME, familyName)
                .header(HEADER_CLAIMS_GIVEN_NAME, givenName)
                .header(HEADER_CLAIMS_EMPLOYEE_ID, userId))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.refreshTime").value(30));
    }

    @Test
    void shouldGetStocksLabels() throws Exception {
        this.mockMvc.perform(get("/stockslabels"))
                .andDo(print())
                .andExpect(status().isForbidden());

        this.mockMvc.perform(get("/stockslabels")
                .header(HEADER_SUB, UID)
                .header(HEADER_SCOPES, scope)
                .header(HEADER_TOKEN, token)
                .header(HEADER_CLAIMS_SITEID, siteId)
                .header(HEADER_CLAIMS_FAMILY_NAME, familyName)
                .header(HEADER_CLAIMS_GIVEN_NAME, givenName)
                .header(HEADER_CLAIMS_EMPLOYEE_ID, userId))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.newLabel").value("Neuf"))
                .andExpect(jsonPath("$.unwrappedLabel").value("Neuf déballé"))
                .andExpect(jsonPath("$.reconditionedLabel").value("Bonne affaire"))
                .andExpect(jsonPath("$.exhibitLabel").value("Expo"));
    }

    @Test
    void shouldSaveAWithdrawalTicket() throws Exception {
        WithdrawalTicket ticketToSave = TestUtils
                .loadTestResource(objectMapper, "/seeds/in/Exemple_Evt_RetraitComptoir.json", WithdrawalTicket.class, StoredTicketToStoreControllerTest.class);

        this.mockMvc.perform(post("/ticketWithdrawal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketToSave)))
                .andExpect(status().isForbidden());

        this.mockMvc.perform(post("/ticketWithdrawal")
                .header(HEADER_SUB, UID)
                .header(HEADER_SCOPES, scope)
                .header(HEADER_TOKEN, token)
                .header(HEADER_CLAIMS_SITEID, siteId)
                .header(HEADER_CLAIMS_FAMILY_NAME, familyName)
                .header(HEADER_CLAIMS_GIVEN_NAME, givenName)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketToSave)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        StoredTicket savedTicket1 = storedTicketMongoRepository.findByEventId("F905010203-1-W");
        assertThat(savedTicket1).isNotNull();
        assertThat(savedTicket1.getSaleId()).isEqualTo("F905010203");
        assertThat(savedTicket1.getLine()).isEqualTo(1);

        StoredTicket savedTicket2 = storedTicketMongoRepository.findByEventId("F905010203-2-W");
        assertThat(savedTicket2).isNotNull();
        assertThat(savedTicket2.getSaleId()).isEqualTo("F905010203");
        assertThat(savedTicket2.getLine()).isEqualTo(2);
    }
}
