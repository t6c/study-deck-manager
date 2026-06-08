package com.studydeck.repository;

import com.studydeck.entity.StudySet;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.function.Supplier;

public class StudySetRepository extends JpaRepository<StudySet> {

    public StudySetRepository(Supplier<EntityManager> entityManagerSupplier) {
        super(entityManagerSupplier, StudySet.class);
    }

    public Optional<StudySet> findByIdWithFlashcards(Long id) {
        try (EntityManager entityManager = getEntityManagerSupplier().get()) {
            return entityManager
                    .createQuery(
                            "select distinct s from StudySet s " +
                                    "left join fetch s.flashcards " +
                                    "where s.id = :id",
                            StudySet.class
                    )
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst();
        }
    }
}
