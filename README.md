# api-supplychain-storebackoffice-eticket-mongo

This API provides a way to save and obtain the tickets stored in the database eticket and referential data for the front eticket.

[![pipeline status](https://gitlab.boul.svc.meshcore.net/apis/supplychain/storebackoffice/api-supplychain-storebackoffice-eticket-mongo/badges/develop/pipeline.svg)](https://gitlab.boul.svc.meshcore.net/apis/supplychain/storebackoffice/api-supplychain-storebackoffice-eticket-mongo/commits/develop)
[![Quality Gate Status](https://pic.boul.svc.meshcore.net/sonar/api/project_badges/measure?branch=develop&project=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&metric=alert_status)](https://pic.boul.svc.meshcore.net/sonar/dashboard?id=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&branch=develop)
[![Coverage](https://pic.boul.svc.meshcore.net/sonar/api/project_badges/measure?branch=develop&project=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&metric=coverage)](https://pic.boul.svc.meshcore.net/sonar/dashboard?id=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&branch=develop)
[![Reliability Rating](https://pic.boul.svc.meshcore.net/sonar/api/project_badges/measure?branch=develop&project=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&metric=reliability_rating)](https://pic.boul.svc.meshcore.net/sonar/dashboard?id=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&branch=develop)
[![Security Rating](https://pic.boul.svc.meshcore.net/sonar/api/project_badges/measure?branch=develop&project=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&metric=security_rating)](https://pic.boul.svc.meshcore.net/sonar/dashboard?id=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&branch=develop)
[![Maintainability Rating](https://pic.boul.svc.meshcore.net/sonar/api/project_badges/measure?branch=develop&project=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&metric=sqale_rating)](https://pic.boul.svc.meshcore.net/sonar/dashboard?id=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&branch=develop)
[![Code Smells](https://pic.boul.svc.meshcore.net/sonar/api/project_badges/measure?branch=develop&project=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&metric=code_smells)](https://pic.boul.svc.meshcore.net/sonar/dashboard?id=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&branch=develop)
[![Bugs](https://pic.boul.svc.meshcore.net/sonar/api/project_badges/measure?branch=develop&project=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&metric=bugs)](https://pic.boul.svc.meshcore.net/sonar/dashboard?id=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&branch=develop)
[![Vulnerabilities](https://pic.boul.svc.meshcore.net/sonar/api/project_badges/measure?branch=develop&project=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&metric=vulnerabilities)](https://pic.boul.svc.meshcore.net/sonar/dashboard?id=com.boulanger.supplychain.storebackoffice.eticket%3Aapi-supplychain-storebackoffice-eticket-mongo&branch=develop)

## Run locally

### Prerequisites

* Having a MongoDB instance that runs locally
* Initialize Nomenclature, Translation, Parameters and StocksLabels collections

### Run configuration

Add the following VM arguments :

```
-Dspring.profiles.active=local
```

Or activate Spring Boot profile : local.

### Test the API

* Start a Mongo DB named eticket
* Start the API with the local profile activated
* The swagger.json should be available at http://localhost:8080/v3/api-docs

## Data Endpoints

---
### Save a ticket in the database

``` POST /ticket ```

with the ticket in the body
and the following header:
 - X-Auth-sub

 Format: TicketToStore
 return: a Stored Ticket

 ---
 ### Get a ticket. The status of this ticket pass from 1 to 2, and an historisation is added to the Historisation collection of the database.

 ``` GET /ticket/{eventId} ```
with the eventId of the ticket,
the language as "lang" request param
and the following headers:
 - X-Auth-sub
 - X-Auth-employee_id
 - X-Auth-given_name
 - X-Auth-family_name

 return: a Ticket

---
### Get the list of tickets for the user site, with a status at 1, 2 and 3 (this last one for a ticket assigned to this user)

``` GET /site/{siteId} ```
with the siteId of the user,
the language as "lang" request param
and the following headers:
 - X-Auth-sub
 - X-Auth-employee_id

 return: a list of Tickets

---
### Update fields of a ticket. If the status is changed, an historisation is added to the Historisation collection of the database.

 ``` PUT /ticket/{eventId} ```
with the eventId of the ticket,
the language as "lang" request param
and the following header:
 - X-Auth-sub

 Format: Ticket
 return: a copy of the updated Ticket

---
### Get the eTicket Front parameters

``` GET /parameters ```
with the following header:
 - X-Auth-sub

return: Parameters

---
### Get the stocks labels

``` GET /stockslabels ```
with the following header:
 - X-Auth-sub

return: StocksLabels

---

## Examples

[StoredTicket (= TicketToStore)](./src/test/resources/seeds/mongo/ticketToSave.json)

[Ticket](./src/test/resources/seeds/mongo/ticket.json)

[Historisation](./src/test/resources/seeds/mongo/historisation.json)

[List of Translation](./src/test/resources/seeds/mongo/translation.json)

[List of Nomenclature](./src/test/resources/seeds/mongo/nomenclature.json)

[Parameters](./src/test/resources/seeds/mongo/parameters.json)

[StocksLabels](./src/test/resources/seeds/mongo/stocks-labels.json)
