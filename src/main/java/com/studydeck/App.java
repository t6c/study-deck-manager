package com.studydeck;

import com.studydeck.config.JpaUtil;
import com.studydeck.console.ConsoleApp;
import com.studydeck.repository.FlashcardRepository;
import com.studydeck.repository.FolderRepository;
import com.studydeck.repository.StudySetRepository;
import com.studydeck.service.FlashcardService;
import com.studydeck.service.FolderService;
import com.studydeck.service.MockDataService;
import com.studydeck.service.StudySetService;

public class App {

    public static void main(String[] args) {
        System.setProperty("org.jboss.logging.provider", "slf4j");

        FolderRepository folderRepository = new FolderRepository(JpaUtil::getEntityManager);
        StudySetRepository studySetRepository = new StudySetRepository(JpaUtil::getEntityManager);
        FlashcardRepository flashcardRepository = new FlashcardRepository(JpaUtil::getEntityManager);
        FolderService folderService = new FolderService(folderRepository);
        StudySetService studySetService = new StudySetService(studySetRepository);
        FlashcardService flashcardService = new FlashcardService(flashcardRepository);

        ConsoleApp consoleApp = new ConsoleApp(
                folderService,
                studySetService,
                flashcardService,
                new MockDataService(folderService, studySetService, flashcardService),
                studySetRepository,
                flashcardRepository
        );

        try {
            consoleApp.run();
        } finally {
            JpaUtil.close();
        }
    }
}
