package com.example.vt6002cem_assignment2.Sign;


import org.junit.Assert;
import org.junit.Test;

public class UserTest {



    @Test
    public void User() {
        User user = new User("Tim","66667777");
        Assert.assertEquals("Tim",user.getName());
        Assert.assertEquals("66667777",user.getPhone());
        User user1 = new User();
        user1.setName("Tim1");
        user1.setPhone("11223344");

    }


}