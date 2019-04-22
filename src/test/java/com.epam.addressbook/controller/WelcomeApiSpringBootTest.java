package com.epam.addressbook.controller;

import com.epam.addressbook.AddressBookApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddressBookApplication.class, webEnvironment = RANDOM_PORT)
public class WelcomeApiSpringBootTest {

    @LocalServerPort
    private String port;
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        final RestTemplateBuilder builder = new RestTemplateBuilder()
            .rootUri("http://localhost:" + port)
            .basicAuthentication("user", "password");
        restTemplate = new TestRestTemplate(builder);
    }

    @Test
    public void sendGetRequest_whenEnvironmentVariableIsSet_returnsWelcomeMessage() {
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).isEqualTo("Hello from test");
    }
}