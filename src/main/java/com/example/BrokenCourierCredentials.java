package com.example;

public class BrokenCourierCredentials {

    public final String login;


    public BrokenCourierCredentials(String login) {
        this.login = login;
    }

    public static BrokenCourierCredentials from(BrokenCourier brokenCourier) {
        return new BrokenCourierCredentials (brokenCourier.login);
    }
}

