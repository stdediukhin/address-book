package com.epam.addressbook;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddressBookApplication.class, webEnvironment = RANDOM_PORT)
public class SecurityApiSpringBootTest {

    @LocalServerPort
    private String port;
    private TestRestTemplate authorizedRestTemplate;

    @Autowired
    private TestRestTemplate unAuthorizedRestTemplate;

    @Before
    public void setUp() {
        RestTemplateBuilder builder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + port)
                .basicAuthorization("user", "password");

        authorizedRestTemplate = new TestRestTemplate(builder);
    }

    @Test
    public void requestToApplication_whenUserIsUnauthorized_returnsUnauthorized() {
        ResponseEntity<String> response = this.unAuthorizedRestTemplate.getForEntity("/", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void requestToApplication_whenUserIsAuthorized_returnsOK() {
        ResponseEntity<String> response = this.authorizedRestTemplate.getForEntity("/", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}