package com.example.vt6002cem_assignment2.home;

import org.junit.Assert;
import org.junit.Test;

public class NewInformationTest {

    @Test
    public void constructor() {
        NewInformation event = new NewInformation(123);

        Assert.assertEquals(event.getImage(),123);
    }

}