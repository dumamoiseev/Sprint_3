package com.example;

import org.apache.commons.lang3.RandomStringUtils;

public class BrokenCourier {
    public String login;


    public BrokenCourier (String login ) {
        this.login = login;

    }

    public static BrokenCourier getbrokenCourier() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        return new BrokenCourier(login);
    }
}
