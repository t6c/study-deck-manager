package com.studydeck.repository;

import com.studydeck.entity.Folder;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.function.Supplier;

public class FolderRepository extends JpaRepository<Folder> {

    public FolderRepository(Supplier<EntityManager> entityManagerSupplier) {
        super(entityManagerSupplier, Folder.class);
    }

    public Optional<Folder> findByIdWithStudySets(Long id) {
        try (EntityManager em = getEntityManagerSupplier().get()) {
            String jpql = "SELECT f FROM Folder f LEFT JOIN FETCH f.studySets WHERE f.id = :id";
            return em.createQuery(jpql, Folder.class)
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst();
        }
    }
}
