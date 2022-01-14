package com.example.vt6002cem_assignment2.product;

import org.junit.Assert;
import org.junit.Test;

public class ProductTest {

    @Test
    public void Product() {
        Product products = new Product();
        products.setKey("123");

        Product product = new Product("ar","ar","blue","des","img","material","productname","productprice","producttype","size","star");
        product.setKey("123");

        Assert.assertEquals(product.getAr(),"ar");
        Assert.assertEquals(product.getArlink(),"ar");
        Assert.assertEquals(product.getColor(),"blue");
        Assert.assertEquals(product.getDescription(),"des");
        Assert.assertEquals(product.getImg(),"img");
        Assert.assertEquals(product.getMaterial(),"material");
        Assert.assertEquals(product.getProductname(),"productname");
        Assert.assertEquals(product.getProductprice(),"productprice");
        Assert.assertEquals(product.getProducttype(),"producttype");
        Assert.assertEquals(product.getSize(),"size");
        Assert.assertEquals(product.getStar(),"star");
        Assert.assertEquals(product.getKey(),"123");



    }
}