package com.epam.addressbook;

import com.jayway.jsonpath.DocumentContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.jsonpath.JsonPath.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddressBookApplication.class, webEnvironment = RANDOM_PORT)
public class HealthApiSpringBootTest {

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
    public void healthTest() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/actuator/health", String.class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext healthJson = parse(response.getBody());

        assertThat(healthJson.read("$.status", String.class)).isEqualTo("UP");
    }
}
