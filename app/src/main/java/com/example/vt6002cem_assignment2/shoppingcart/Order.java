package com.example.vt6002cem_assignment2.shoppingcart;

public class Order {
    private String color;
    private String img;
    private String productname;
    private String productprice;
    private String producttype;
    private String quantity;
    private String size;
    private String productkey;
    private String datekey;



    public Order() {
    }

    public Order(String color, String img, String productname, String productprice, String producttype, String quantity, String size, String productkey) {
        this.color = color;
        this.img = img;
        this.productname = productname;
        this.productprice = productprice;
        this.producttype = producttype;
        this.quantity = quantity;
        this.size = size;
        this.productkey = productkey;
    }

    public String getColor() {
        return color;
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

    public String getProductkey() {
        return productkey;
    }


    public String getDatekey() {
        return datekey;
    }

    public void setDatekey(String datekey) {
        this.datekey = datekey;
    }




}
