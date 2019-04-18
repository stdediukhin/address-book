package com.epam.addressbook;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("accommodations")
public class AccommodationController {

    private final AccommodationRepository repository;
    private final MeterRegistry meterRegistry;

    @PostMapping
    public ResponseEntity<Accommodation> create(@RequestBody final Accommodation accommodation) {
        return repository.create(accommodation)
            .map(createdAccommodation -> {
                meterRegistry.counter("Accommodation.created").increment();
                meterRegistry.gauge("Accommodation.count", repository.findAll().get().size());

                return createdAccommodation;
            })
            .map(createdAccommodation -> new ResponseEntity<>(createdAccommodation, HttpStatus.CREATED))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @GetMapping("{id}")
    public ResponseEntity<Accommodation> getById(@PathVariable final long id) {
        return repository.getById(id)
            .map(accommodation -> {
                meterRegistry.counter("Accommodation.getById");

                return accommodation;
            })
            .map(accommodation -> new ResponseEntity<>(accommodation, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Accommodation>> findAll() {
        return repository.findAll()
            .map(accommodations -> {
                meterRegistry.counter("Accommodation.findAll").increment();

                return accommodations;
            })
            .map(accommodations -> new ResponseEntity<>(accommodations, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping("{id}")
    public ResponseEntity<Accommodation> update(@PathVariable final long id,
                                                @RequestBody final Accommodation accommodation) {
        return repository.update(id, accommodation)
            .map(updatedAccommodation -> {
                meterRegistry.counter("Accommodation.update").increment();

                return updatedAccommodation;
            })
            .map(updatedAccommodation -> new ResponseEntity<>(updatedAccommodation, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Accommodation> delete(@PathVariable final long id) {
        repository.delete(id);

        meterRegistry.counter("Accommodation.delete").increment();
        meterRegistry.gauge("Accommodation.count", repository.findAll().get().size());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
