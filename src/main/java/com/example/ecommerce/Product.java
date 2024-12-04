package com.example.ecommerce;

/**
 *
 * @author jluis.albarral@gmail.com
 */
public class Product {

    private final int id;
    private final String desc;
    private int amount;

    public Product(int id, String desc, int amount) {
        this.id = id;
        this.desc = desc;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
