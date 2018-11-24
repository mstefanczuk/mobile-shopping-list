package pl.edu.pjatk.stefanczuk.shoppinglist.model;

import lombok.Data;

@Data
public class Product {
    private long id;
    private String name;
    private double price;
    private int quantity;
    private boolean bought;
}
