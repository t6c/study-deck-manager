package com.studydeck.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "folders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudySet> studySets = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public List<StudySet> getStudySets() {
        return Collections.unmodifiableList(studySets);
    }

    public void addStudySet(StudySet studySet) {
        if (studySet == null) {
            throw new IllegalArgumentException("Study set is required.");
        }
        studySets.add(studySet);
        studySet.setFolder(this);
    }

    public void removeStudySet(StudySet studySet) {
        if (studySets.remove(studySet)) {
            studySet.setFolder(null);
        }
    }
}
