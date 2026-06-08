package com.studydeck.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class JpaRepository<T> implements CrudRepository<T> {

    private final Supplier<EntityManager> entityManagerSupplier;
    private final Class<T> entityClass;

    public JpaRepository(Supplier<EntityManager> entityManagerSupplier, Class<T> entityClass) {
        this.entityManagerSupplier = entityManagerSupplier;
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        return executeInTransaction(entityManager -> entityManager.merge(entity));
    }

    @Override
    public Optional<T> findById(Long id) {
        try (EntityManager entityManager = entityManagerSupplier.get()) {
            return Optional.ofNullable(entityManager.find(entityClass, id));
        }
    }

    @Override
    public List<T> findAll() {
        try (EntityManager entityManager = entityManagerSupplier.get()) {
            String jpql = "select e from " + entityClass.getSimpleName() + " e order by e.id";
            return entityManager.createQuery(jpql, entityClass).getResultList();
        }
    }

    @Override
    public void deleteById(Long id) {
        executeInTransaction(entityManager -> {
            T entity = entityManager.find(entityClass, id);
            if (entity != null) {
                entityManager.remove(entity);
            }
            return null;
        });
    }

    protected <R> R executeInTransaction(Function<EntityManager, R> action) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = entityManagerSupplier.get()) {
            transaction = entityManager.getTransaction();
            transaction.begin();
            R result = action.apply(entityManager);
            transaction.commit();
            return result;
        } catch (RuntimeException exception) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    protected Supplier<EntityManager> getEntityManagerSupplier() {
        return entityManagerSupplier;
    }

}
