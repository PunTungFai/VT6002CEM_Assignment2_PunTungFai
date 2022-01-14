package com.example.vt6002cem_assignment2.product;

import java.io.Serializable;

public class Product implements Serializable {
    private String ar;
    private String arlink;
    private String color;
    private String description;
    private String img;
    private String material;
    private String productname;
    private String productprice;
    private String producttype;
    private String size;
    private String star;
    private String key;

    public Product() {
    }

    public Product(String ar, String arlink, String color, String description, String img, String material, String productname, String productprice, String producttype, String size, String star) {
        this.ar = ar;
        this.arlink = arlink;
        this.color = color;
        this.description = description;
        this.img = img;
        this.material = material;
        this.productname = productname;
        this.productprice = productprice;
        this.producttype = producttype;
        this.size = size;
        this.star = star;
    }

    public String getAr() {
        return ar;
    }

    public String getArlink() {
        return arlink;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }

    public String getMaterial() {
        return material;
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

    public String getSize() {
        return size;
    }

    public String getStar() {
        return star;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
