package com.studydeck.repository;

import com.studydeck.entity.Folder;
import jakarta.persistence.EntityManager;

import java.util.function.Supplier;

public class FolderRepository extends JpaRepository<Folder> {

    public FolderRepository(Supplier<EntityManager> entityManagerSupplier) {
        super(entityManagerSupplier, Folder.class);
    }
}
