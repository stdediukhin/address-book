package com.epam.addressbook;

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
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("accommodations")
public class AccommodationController {

    private final AccommodationRepository repository;

    @PostMapping
    public ResponseEntity<Accommodation> create(@RequestBody final Accommodation toCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(repository.create(toCreate).get());
    }

    @GetMapping("{id}")
    public ResponseEntity<Accommodation> getById(@PathVariable final long id) {
        return ResponseEntity.of(repository.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Accommodation>> findAll() {
        return ResponseEntity.of(repository.findAll());

    }

    @PutMapping("{id}")
    public ResponseEntity<Accommodation> update(@PathVariable final long id,
                                                @RequestBody final Accommodation toUpdate) {
        final Optional<Accommodation> update = repository.update(id, toUpdate);
        if (update.isPresent()) {
            return ResponseEntity.of(update);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Accommodation> delete(@PathVariable final long id) {
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
