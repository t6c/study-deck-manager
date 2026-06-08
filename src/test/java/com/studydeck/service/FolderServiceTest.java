package com.studydeck.service;

import com.studydeck.entity.Folder;
import com.studydeck.repository.InMemoryCrudRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FolderServiceTest {

    @Test
    void createFolderRejectsBlankName() {
        FolderService service = new FolderService(new InMemoryCrudRepository<>());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createFolder(" ")
        );

        assertEquals("Folder name is required.", exception.getMessage());
    }

    @Test
    void createFolderTrimsNameBeforeSaving() {
        FolderService service = new FolderService(new InMemoryCrudRepository<>());

        Folder folder = service.createFolder("  Biology  ");

        assertEquals("Biology", folder.getName());
    }
}
