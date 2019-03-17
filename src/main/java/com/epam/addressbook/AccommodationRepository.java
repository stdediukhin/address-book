package com.epam.addressbook;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository {

    Optional<Accommodation> create(final Accommodation any);
    Optional<Accommodation> getById(final long accommodationId);
    Optional<List<Accommodation>> findAll();
    Optional<Accommodation> update(final long id, final Accommodation toUpdate);
    void delete(final long id);
}
