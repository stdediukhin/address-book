package com.epam.addressbook.actuator;

import com.epam.addressbook.repository.AccommodationRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class AccommodationHealthIndicator implements HealthIndicator {

    private static final int MAX_TIME_ENTRIES = 5;
    private final AccommodationRepository accommodationRepository;

    public AccommodationHealthIndicator(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();

        return (accommodationRepository.findAll().get().size() < MAX_TIME_ENTRIES) ? builder.up().build() : builder.down().build();
    }
}
