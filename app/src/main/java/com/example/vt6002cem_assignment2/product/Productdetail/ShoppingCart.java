package com.example.vt6002cem_assignment2.product.Productdetail;

public class ShoppingCart {
    private String color;
    private String floor;
    private String img;
    private String productname;
    private String productprice;
    private String producttype;
    private String quantity;
    private String size;
    private String key;
    private String subkey;

    public ShoppingCart() {

    }

    public ShoppingCart(String color, String img, String productname, String productprice, String producttype, String quantity, String size) {
        this.color = color;
        this.img = img;
        this.productname = productname;
        this.productprice = productprice;
        this.producttype = producttype;
        this.quantity = quantity;
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public String getFloor() {
        return floor;
    }

    public String getImg() {
        return img;
    }

    public String getProductname() {
        return productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public String getProducttype() {
        return producttype;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSize() {
        return size;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSubkey() {
        return subkey;
    }

    public void setSubkey(String subkey) {
        this.subkey = subkey;
    }





}
