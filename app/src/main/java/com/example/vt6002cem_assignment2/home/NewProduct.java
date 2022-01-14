package com.example.vt6002cem_assignment2.home;

public class NewProduct {

    int image;
    String title,description;

    public NewProduct(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
