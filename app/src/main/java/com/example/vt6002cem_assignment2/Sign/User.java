package com.example.vt6002cem_assignment2.Sign;

public class User {

    String name,phone;

    public User( String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public User() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
