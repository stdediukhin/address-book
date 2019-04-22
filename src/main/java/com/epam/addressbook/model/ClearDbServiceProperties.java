package com.epam.addressbook.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.validation.constraints.NotNull;

@Data
@Configuration
@ConfigurationProperties(prefix = "vcap.services.cleardb.credentials")
@Profile("cloud")
public class ClearDbServiceProperties {

    @NotNull
    private String jdbcUrl;
}
