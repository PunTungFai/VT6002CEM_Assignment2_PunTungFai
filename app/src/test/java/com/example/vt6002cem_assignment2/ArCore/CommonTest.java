package com.example.vt6002cem_assignment2.ArCore;

import org.junit.Assert;
import org.junit.Test;

public class CommonTest {


    @Test
    public void Common() {
        Common.model = "URL";
        Assert.assertEquals(Common.model,"URL");

    }

}