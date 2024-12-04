package com.example.ecommerce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jluis.albarral@gmail.com
 */
@Service
public class CartService {

    Logger logger = LoggerFactory.getLogger(CartService.class);
    private Map<Integer, Cart> cartRepo;    // carts repository (volatile, not persisted in DB)
    private final AtomicInteger counter = new AtomicInteger();

    @Autowired
    public CartService() {
        cartRepo = new HashMap<>();
    }

    // get all carts in repo
    public List<Cart> getAllCarts() {
        return cartRepo.values().stream().collect(Collectors.toList());
    }

    // get cart with given id
    public Cart getCartById(Integer id) {
        return cartRepo.get(id);
    }

    // create an empty cart
    public Cart createCart() {
        try {
            Integer id = counter.incrementAndGet();
            Cart cart = new Cart(id);
            cartRepo.put(id, cart);
            logger.info("Created cart " + id);
            return cart;
        } catch (Exception ex) {
            logger.error("Failed cart creation", ex);
            return null;
        }
    }

    // add products to cart with given id
    public Cart updateCart(Integer id, List<Product> products) {
        try {
            if (cartRepo.containsKey(id)) {
                Cart cart = cartRepo.get(id);
                products.forEach(product -> cart.addProduct(product));
                logger.info("Updated cart " + id);
                return cart;
            } else {
                logger.warn("Failed cart update: no cart found with id " + id);
                return null;
            }
        } catch (Exception ex) {
            logger.error("Failed cart update", ex);
            return null;
        }
    }

    // delete cart with given id
    public boolean deleteCart(Integer id) {
        try {
            if (cartRepo.containsKey(id)) {
                cartRepo.remove(id);
                logger.info("Deleted cart " + id);
                return true;
            } else {
                logger.warn("Failed cart delete: no cart found with id " + id);
                return false;
            }
        } catch (Exception ex) {
            logger.error("Failed cart delete", ex);
            return false;
        }
    }

    // delete all carts in repo
    public void deleteAll() {
        cartRepo.clear();
        logger.info("All carts deleted");
    }
}
