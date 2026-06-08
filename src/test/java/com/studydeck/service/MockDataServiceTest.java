package com.studydeck.service;

import com.studydeck.entity.Folder;
import com.studydeck.entity.StudySet;
import com.studydeck.repository.InMemoryCrudRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MockDataServiceTest {

    @Test
    void seedCreatesFoldersStudySetsAndFlashcards() {
        InMemoryCrudRepository<Folder> folderRepository = new InMemoryCrudRepository<>();
        InMemoryCrudRepository<StudySet> studySetRepository = new InMemoryCrudRepository<>();
        MockDataService service = new MockDataService(
                new FolderService(folderRepository),
                new StudySetService(studySetRepository),
                new FlashcardService(new InMemoryCrudRepository<>())
        );

        MockDataService.SeedResult result = service.seed();

        assertEquals(3, result.foldersCreated());
        assertEquals(6, result.studySetsCreated());
        assertEquals(42, result.flashcardsCreated());
        assertEquals(3, folderRepository.findAll().size());
        assertEquals(6, studySetRepository.findAll().size());
    }

    @Test
    void seedDoesNotDuplicateExistingMockData() {
        InMemoryCrudRepository<Folder> folderRepository = new InMemoryCrudRepository<>();
        MockDataService service = new MockDataService(
                new FolderService(folderRepository),
                new StudySetService(new InMemoryCrudRepository<>()),
                new FlashcardService(new InMemoryCrudRepository<>())
        );

        service.seed();
        MockDataService.SeedResult result = service.seed();

        assertEquals(0, result.foldersCreated());
        assertEquals(0, result.studySetsCreated());
        assertEquals(0, result.flashcardsCreated());
        assertEquals(3, folderRepository.findAll().size());
    }
}
