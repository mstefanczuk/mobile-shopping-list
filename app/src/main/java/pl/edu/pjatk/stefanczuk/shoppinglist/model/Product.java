package pl.edu.pjatk.stefanczuk.shoppinglist.model;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private int price;
    private int quantity;
    private boolean bought;
}
