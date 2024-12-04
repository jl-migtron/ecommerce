/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ecommerce;

/**
 *
 * @author jluis.albarral@gmail.com
 */
public class CartNotFoundException extends RuntimeException {

    CartNotFoundException(Integer id) {
        super("Could not find cart " + id);
    }
}
