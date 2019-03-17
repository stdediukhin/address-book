package com.epam.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryAccommodationRepository implements AccommodationRepository {

    private final AtomicLong counter = new AtomicLong();
    private final Map<Long, Accommodation> repo = new HashMap<>();

    public Optional<Accommodation> create(final Accommodation entity) {
        entity.setId(counter.incrementAndGet());
        repo.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    public Optional<Accommodation> getById(final long accommodationId) {
        return Optional.ofNullable(repo.get(accommodationId));
    }

    public Optional<List<Accommodation>> findAll() {
        return Optional.of(new ArrayList<>(repo.values()));
    }

    public Optional<Accommodation> update(final long id, final Accommodation toUpdate) {
        final Optional<Accommodation> accommodation = Optional.ofNullable(repo.get(id));

        if (accommodation.isPresent()) {
            toUpdate.setId(accommodation.get().getId());
            repo.put(toUpdate.getId(), toUpdate);
            return Optional.of(toUpdate);
        }
        return Optional.empty();
    }

    public void delete(final long id) {
        repo.remove(id);
    }

}
