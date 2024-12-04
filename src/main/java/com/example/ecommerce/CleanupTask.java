package com.example.ecommerce;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author jluis.albarral@gmail.com
 */
@Component
public class CleanupTask {

    Logger logger = LoggerFactory.getLogger(CleanupTask.class);
    private final int inactivityMins = 10;

    @Autowired
    private CartService cartService;

    @Scheduled(fixedRate = 10000)
    // periodic cleaning task (every 10 seconds)
    public void cleanInactiveCarts() {
        try {
            // search for inactive carts
            List<Integer> inactives = cartService.getAllCarts().stream().filter(c -> isCartInactiveForLong(c)).map(Cart::getId).collect(Collectors.toList());
            // and delete them 
            if (!inactives.isEmpty()) {
                logger.info("Deleting inactive carts ...");
                inactives.forEach(id -> cartService.deleteCart(id));
            }
        } catch (Exception ex) {
            logger.error("Error cleaning inactive carts", ex);
        }
    }

    // check if cart has been inactive for too long
    private Boolean isCartInactiveForLong(final Cart cart) {
        return LocalDateTime.now().minusMinutes(inactivityMins).isAfter(cart.getTimeStamp());
    }

}
