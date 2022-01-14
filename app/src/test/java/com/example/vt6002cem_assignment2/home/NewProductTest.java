package com.example.vt6002cem_assignment2.home;

import org.junit.Assert;
import org.junit.Test;

public class NewProductTest {

    @Test
    public void constructor() {
        NewProduct event = new NewProduct(123,"title","des");

        Assert.assertEquals(event.getImage(),123);
        Assert.assertEquals(event.getTitle(),"title");
        Assert.assertEquals(event.getDescription(),"des");
    }


}