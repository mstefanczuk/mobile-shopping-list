package pl.edu.pjatk.stefanczuk.shoppinglist.model;

import lombok.Data;

@Data
public class Shop {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private int radius;
    private String helloMessage;
    private String promoMessage;
    private String goodbyeMessage;
    private boolean favourite;
}
