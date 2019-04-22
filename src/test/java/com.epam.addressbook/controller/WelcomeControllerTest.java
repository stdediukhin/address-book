package com.epam.addressbook.controller;

import com.epam.addressbook.controller.WelcomeController;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WelcomeControllerTest {
    @Test
    public void sayHello_whenWelcomeMessageIsSet_returnsWelcomeMessage() {
        WelcomeController controller = new WelcomeController("A welcome message");

        assertThat(controller.sayHello()).isEqualTo("A welcome message");
    }
}