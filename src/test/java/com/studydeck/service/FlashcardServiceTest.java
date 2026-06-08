package com.studydeck.service;

import com.studydeck.entity.Flashcard;
import com.studydeck.entity.StudySet;
import com.studydeck.repository.InMemoryCrudRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FlashcardServiceTest {

    @Test
    void createFlashcardRejectsBlankTerm() {
        FlashcardService service = new FlashcardService(new InMemoryCrudRepository<>());
        StudySet studySet = new StudySet("Java Basics");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createFlashcard(studySet, " ", "Object Relational Mapping")
        );

        assertEquals("Term is required.", exception.getMessage());
    }

    @Test
    void createFlashcardLinksToStudySet() {
        FlashcardService service = new FlashcardService(new InMemoryCrudRepository<>());
        StudySet studySet = new StudySet("Java Basics");

        Flashcard flashcard = service.createFlashcard(studySet, " ORM ", " Maps objects to database tables ");

        assertEquals("ORM", flashcard.getTerm());
        assertEquals("Maps objects to database tables", flashcard.getDefinition());
        assertSame(studySet, flashcard.getStudySet());
        assertEquals(1, studySet.getFlashcards().size());
    }
}
