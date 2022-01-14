package com.example.vt6002cem_assignment2.shoppingcart;

import org.junit.Assert;
import org.junit.Test;

public class OrderTest {
    @Test
    public void Product() {
        Order order = new Order();
        order.setDatekey("123");
        order.getDatekey();

        Order order1 = new Order("blue","img","productname","productprice","producttype","quantity","size","productkey");

        Assert.assertEquals(order1.getColor(),"blue");
        Assert.assertEquals(order1.getImg(),"img");
        Assert.assertEquals(order1.getProductname(),"productname");
        Assert.assertEquals(order1.getProductprice(),"productprice");
        Assert.assertEquals(order1.getProducttype(),"producttype");
        Assert.assertEquals(order1.getQuantity(),"quantity");
        Assert.assertEquals(order1.getSize(),"size");
        Assert.assertEquals(order1.getProductkey(),"productkey");;




    }
}

