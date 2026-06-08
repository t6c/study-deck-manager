package com.studydeck.service;

import com.studydeck.entity.Folder;
import com.studydeck.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public class FolderService {

    private final CrudRepository<Folder> folderRepository;

    public FolderService(CrudRepository<Folder> folderRepository) {
        this.folderRepository = folderRepository;
    }

    public Folder createFolder(String name) {
        String cleanedName = requireText(name, "Folder name is required.");
        return folderRepository.save(new Folder(cleanedName));
    }

    public Folder updateFolder(Long id, String name) {
        Folder folder = getFolderOrThrow(id);
        folder.setName(requireText(name, "Folder name is required."));
        return folderRepository.save(folder);
    }

    public Optional<Folder> findFolder(Long id) {
        return folderRepository.findById(id);
    }

    public List<Folder> listFolders() {
        return folderRepository.findAll();
    }

    public void deleteFolder(Long id) {
        folderRepository.deleteById(id);
    }

    private Folder getFolderOrThrow(Long id) {
        return folderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Folder was not found."));
    }

    private static String requireText(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}
