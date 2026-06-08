package com.studydeck.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "study_sets")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudySet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @OneToMany(mappedBy = "studySet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flashcard> flashcards = new ArrayList<>();

    public StudySet(String title) {
        this.title = title;
    }

    public List<Flashcard> getFlashcards() {
        return Collections.unmodifiableList(flashcards);
    }

    public void addFlashcard(Flashcard flashcard) {
        if (flashcard == null) {
            throw new IllegalArgumentException("Flashcard is required.");
        }
        flashcards.add(flashcard);
        flashcard.setStudySet(this);
    }

    public void removeFlashcard(Flashcard flashcard) {
        if (flashcards.remove(flashcard)) {
            flashcard.setStudySet(null);
        }
    }
}
