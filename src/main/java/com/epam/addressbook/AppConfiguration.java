package com.epam.addressbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.sql.DataSource;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
public class AppConfiguration {

    @Bean
    @DependsOn("accommodationRepository")
    public HealthIndicator accommodationHealthIndicator(final AccommodationRepository accommodationRepository) {
        return new AccommodationHealthIndicator(accommodationRepository);
    }

    @Bean
    public ObjectMapper jsonObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
            .serializationInclusion(NON_NULL)
            .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS)
            .modules(new JavaTimeModule())
            .build();
    }

    @Configuration
    @Profile("!cloud")
    public static class LocalConfiguration {

        @Bean
        public AccommodationRepository accommodationRepository() {
            final MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUrl(System.getenv("SPRING_DATASOURCE_URL"));
            return new JdbcAccommodationRepository(dataSource);
        }
    }

    @Configuration
    @Profile("cloud")
    public static class CloudConfiguration {

        @Bean
        public AccommodationRepository accommodationRepository(DataSource dataSource) {
            return new JdbcAccommodationRepository(dataSource);
        }

    }
}
