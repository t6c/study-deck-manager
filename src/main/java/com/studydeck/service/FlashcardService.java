package com.studydeck.service;

import com.studydeck.entity.Flashcard;
import com.studydeck.entity.StudySet;
import com.studydeck.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public class FlashcardService {

    private final CrudRepository<Flashcard> flashcardRepository;

    public FlashcardService(CrudRepository<Flashcard> flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public Flashcard createFlashcard(StudySet studySet, String term, String definition) {
        if (studySet == null) {
            throw new IllegalArgumentException("Study set is required.");
        }
        Flashcard flashcard = new Flashcard(
                requireText(term, "Term is required."),
                requireText(definition, "Definition is required.")
        );
        studySet.addFlashcard(flashcard);
        return flashcardRepository.save(flashcard);
    }

    public Flashcard updateFlashcard(Long id, String term, String definition) {
        Flashcard flashcard = getFlashcardOrThrow(id);
        flashcard.setTerm(requireText(term, "Term is required."));
        flashcard.setDefinition(requireText(definition, "Definition is required."));
        return flashcardRepository.save(flashcard);
    }

    public Optional<Flashcard> findFlashcard(Long id) {
        return flashcardRepository.findById(id);
    }

    public List<Flashcard> listFlashcards() {
        return flashcardRepository.findAll();
    }

    public void deleteFlashcard(Long id) {
        flashcardRepository.deleteById(id);
    }

    private Flashcard getFlashcardOrThrow(Long id) {
        return flashcardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard was not found."));
    }

    private static String requireText(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}
