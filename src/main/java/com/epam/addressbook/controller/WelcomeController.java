package com.epam.addressbook.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    private final String helloMessage;

    public WelcomeController(@Value("${WELCOME_MESSAGE}") final String helloMessage) {
        this.helloMessage = helloMessage;
    }

    @GetMapping
    public String sayHello() {
        return helloMessage;
    }
}
