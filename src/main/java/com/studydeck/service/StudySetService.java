package com.studydeck.service;

import com.studydeck.entity.Folder;
import com.studydeck.entity.StudySet;
import com.studydeck.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public class StudySetService {

    private final CrudRepository<StudySet> studySetRepository;

    public StudySetService(CrudRepository<StudySet> studySetRepository) {
        this.studySetRepository = studySetRepository;
    }

    public StudySet createStudySet(Folder folder, String title) {
        if (folder == null) {
            throw new IllegalArgumentException("Folder is required.");
        }
        StudySet studySet = new StudySet(requireText(title, "Study set title is required."));
        folder.addStudySet(studySet);
        return studySetRepository.save(studySet);
    }

    public StudySet updateStudySet(Long id, String title) {
        StudySet studySet = getStudySetOrThrow(id);
        studySet.setTitle(requireText(title, "Study set title is required."));
        return studySetRepository.save(studySet);
    }

    public Optional<StudySet> findStudySet(Long id) {
        return studySetRepository.findById(id);
    }

    public List<StudySet> listStudySets() {
        return studySetRepository.findAll();
    }

    public void deleteStudySet(Long id) {
        studySetRepository.deleteById(id);
    }

    private StudySet getStudySetOrThrow(Long id) {
        return studySetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Study set was not found."));
    }

    private static String requireText(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}
