package com.example.vt6002cem_assignment2.order_record;

import com.example.vt6002cem_assignment2.shoppingcart.Order;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class RecordTest {
    private ArrayList<Order> orders = new ArrayList<>();;


    @Test
    public void constructor() {
        Order order = new Order("blue","imgURL","Tim","240","Man","1","M","0001");
        order.setDatekey("123");

        orders.add(order);



        Record record = new Record("123",orders);


    }

    @Test
    public void test() {
        Order order = new Order("blue","imgURL","Tim","240","Man","1","M","0001");
        order.setDatekey("123");
        Record record = new Record();
        record.order.add(order);
        Assert.assertEquals(record.getOrder().get(0).getColor(),order.getColor());

        Assert.assertEquals(record.getlistsize(),1);
        Assert.assertEquals(Double.toString(record.getamount()),"240.0");




    }

    @Test
    public void Datekey() {
        Record record = new Record();
        record.setDatekey("123");
        Assert.assertEquals(record.getDatekey(),"123");




    }


}