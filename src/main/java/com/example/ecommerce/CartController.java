package com.example.ecommerce;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jluis.albarral@gmail.com
 */
@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cart> getAllCarts() {
        // Get all carts
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Integer id) {
        // Get cart by its ID 
        Cart cart = cartService.getCartById(id);
        // Returns the cart if found; otherwise, returns a 404 Not Found response
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            throw (new CartNotFoundException(id));
        }
    }

    @PostMapping
    public ResponseEntity<Cart> createCart() {
        // Creates a new cart and returns the created cart
        Cart cart = cartService.createCart();
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Integer id, @RequestBody List<Product> products) {
        // Updates the given cart and returns the updated cart
        Cart cart = cartService.updateCart(id, products);
        // Returns the cart if found; otherwise, returns a 404 Not Found response
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            throw (new CartNotFoundException(id));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Integer id) {
        // Deletes the cart with the specified ID using the service layer
        if (cartService.deleteCart(id)) {
            // Returns a 204 No Content response if successful deletion
            return ResponseEntity.noContent().build();
        } else {
            //  otherwise, returns a 404 Not Found response
            throw (new CartNotFoundException(id));
        }
    }

}
