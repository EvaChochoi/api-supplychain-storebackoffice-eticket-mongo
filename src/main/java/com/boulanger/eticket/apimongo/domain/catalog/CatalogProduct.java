
/*
 * -----------------------------------------------------------------
 *  Ce code source est la propriété de Boulanger S.A. Tous droits réservés, 2021.
 *  (C) Copyright Boulanger S.A., 2021
 * -----------------------------------------------------------------
 */

package com.boulanger.eticket.apimongo.domain.catalog;


import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Product class for API Catalog
 */
@Document(collection = "ticket")
@Value
@Builder(toBuilder = true)
public class CatalogProduct {

    private Product product;


    @Value
    @Builder(toBuilder = true)
    public static class Product {
        private final String productSkuIds;
        private final String productSapId;
        private final int quantity;
    }


}