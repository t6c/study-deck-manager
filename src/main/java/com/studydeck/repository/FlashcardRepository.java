package com.studydeck.repository;

import com.studydeck.entity.Flashcard;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.function.Supplier;

public class FlashcardRepository extends JpaRepository<Flashcard> {

    public FlashcardRepository(Supplier<EntityManager> entityManagerSupplier) {
        super(entityManagerSupplier, Flashcard.class);
    }

    public List<Flashcard> findByStudySetId(Long studySetId) {
        try (EntityManager entityManager = getEntityManagerSupplier().get()) {
            return entityManager
                    .createQuery(
                            "select f from Flashcard f where f.studySet.id = :studySetId order by f.id",
                            Flashcard.class
                    )
                    .setParameter("studySetId", studySetId)
                    .getResultList();
        }
    }
}
