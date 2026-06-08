package com.studydeck.service;

import com.studydeck.entity.Folder;
import com.studydeck.entity.StudySet;
import com.studydeck.repository.InMemoryCrudRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudySetServiceTest {

    @Test
    void createStudySetRejectsMissingFolder() {
        StudySetService service = new StudySetService(new InMemoryCrudRepository<>());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createStudySet(null, "Japanese")
        );

        assertEquals("Folder is required.", exception.getMessage());
    }

    @Test
    void createStudySetLinksToFolder() {
        StudySetService service = new StudySetService(new InMemoryCrudRepository<>());
        Folder folder = new Folder("Languages");

        StudySet studySet = service.createStudySet(folder, "  Japanese N5  ");

        assertEquals("Japanese N5", studySet.getTitle());
        assertSame(folder, studySet.getFolder());
        assertEquals(1, folder.getStudySets().size());
    }
}
