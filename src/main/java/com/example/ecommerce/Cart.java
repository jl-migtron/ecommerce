package com.example.ecommerce;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jluis.albarral@gmail.com
 */
public class Cart {

    Logger logger = LoggerFactory.getLogger(Cart.class);
    private final Integer id;
    private LocalDateTime timeStamp;
    private Map<Integer, Product> products;

    Cart(Integer id) {
        this.id = id;
        this.timeStamp = LocalDateTime.now();
        this.products = new HashMap<>();
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void addProduct(final Product product) {
        try {
            // if product already exists, update the amount
            if (products.containsKey(product.getId())) {
                Product existingProduct = products.get(product.getId());
                existingProduct.setAmount(existingProduct.getAmount() + product.getAmount());
                logger.debug("Updated product " + product.getId());
            } else {
                // otherwise just insert product
                products.put(product.getId(), product);
                logger.debug("Added product " + product.getId());
            }
            // refresh the timestamp on every update
            this.timeStamp = LocalDateTime.now();
        } catch (Exception ex) {
            logger.error("Error adding product ", ex);
        }
    }

}
