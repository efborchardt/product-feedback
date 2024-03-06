package com.efborchardt.productfeedback.domain._shared.repository;

import java.util.Optional;
import java.util.UUID;

public interface DefaultRepository<T> {

    void save(T entity);
    Optional<T> findById(UUID id);
}
