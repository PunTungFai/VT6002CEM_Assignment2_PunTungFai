package com.example.vt6002cem_assignment2.order_record;

import com.example.vt6002cem_assignment2.shoppingcart.Order;

import java.util.ArrayList;

public class Record {
    private String datekey;
    public ArrayList<Order> order = new ArrayList<>();


    public Record() {
    }

    public Record(String datekey, ArrayList<Order> order) {
        this.datekey = datekey;
        this.order = order;
    }

    public String getDatekey() {
        return datekey;
    }

    public void setDatekey(String datekey) {
        this.datekey = datekey;
    }

    public ArrayList<Order> getOrder() {
        return order;
    }

    //Count the ArrayList order size
    public int getlistsize(){
        return order.size();
    }

    //Count the product order amount
    public double getamount(){
        double amountprice = 0;
        for(int i=0; i<order.size();i++){
            amountprice= amountprice+Double.parseDouble(order.get(i).getProductprice())*Double.parseDouble(order.get(i).getQuantity());
        }
        return amountprice;
    }


}
