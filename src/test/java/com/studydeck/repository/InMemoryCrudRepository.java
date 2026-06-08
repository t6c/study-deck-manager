package com.studydeck.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryCrudRepository<T> implements CrudRepository<T> {

    private final List<T> entities = new ArrayList<>();

    @Override
    public T save(T entity) {
        entities.add(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(entities);
    }

    @Override
    public void deleteById(Long id) {
    }
}
