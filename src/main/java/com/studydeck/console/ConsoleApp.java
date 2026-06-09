package com.studydeck.console;

import com.studydeck.entity.Flashcard;
import com.studydeck.entity.Folder;
import com.studydeck.entity.StudySet;
import com.studydeck.repository.FlashcardRepository;
import com.studydeck.repository.FolderRepository;
import com.studydeck.repository.StudySetRepository;
import com.studydeck.service.FlashcardService;
import com.studydeck.service.FolderService;
import com.studydeck.service.MockDataService;
import com.studydeck.service.StudySetService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);
    private final FolderService folderService;
    private final StudySetService studySetService;
    private final FlashcardService flashcardService;
    private final MockDataService mockDataService;
    private final StudySetRepository studySetRepository;
    private final FlashcardRepository flashcardRepository;
    private final FolderRepository folderRepository;

    public ConsoleApp(
            FolderService folderService,
            StudySetService studySetService,
            FlashcardService flashcardService,
            MockDataService mockDataService,
            StudySetRepository studySetRepository,
            FlashcardRepository flashcardRepository,
            FolderRepository folderRepository
    ) {
        this.folderService = folderService;
        this.studySetService = studySetService;
        this.flashcardService = flashcardService;
        this.mockDataService = mockDataService;
        this.studySetRepository = studySetRepository;
        this.flashcardRepository = flashcardRepository;
        this.folderRepository = folderRepository;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMainMenu();
            switch (readLine("Choose: ")) {
                case "1" -> manageFolders();
                case "2" -> manageStudySets();
                case "3" -> manageFlashcards();
                case "4" -> viewStudySet();
                case "5" -> seedMockData();
                case "0" -> running = false;
                default -> printMessage("Invalid choice.");
            }
        }
        printMessage("Goodbye.");
    }

    private void printMainMenu() {
        printMessage("");
        printMessage("Study Deck Manager");
        printMessage("1. Manage folders");
        printMessage("2. Manage study sets");
        printMessage("3. Manage flashcards");
        printMessage("4. View study set");
        printMessage("5. Inject mock data");
        printMessage("0. Exit");
    }

    private void manageFolders() {
        boolean managing = true;
        while (managing) {
            printMessage("");
            printMessage("Folders");
            printMessage("1. Create folder");
            printMessage("2. List folders");
            printMessage("3. Update folder");
            printMessage("4. Delete folder");
            printMessage("0. Back");

            try {
                switch (readLine("Choose: ")) {
                    case "1" -> createFolder();
                    case "2" -> listFolders();
                    case "3" -> updateFolder();
                    case "4" -> deleteFolder();
                    case "0" -> managing = false;
                    default -> printMessage("Invalid choice.");
                }
            } catch (IllegalArgumentException exception) {
                printMessage(exception.getMessage());
            }
        }
    }

    private void manageStudySets() {
        boolean managing = true;
        while (managing) {
            printMessage("");
            printMessage("Study Sets");
            printMessage("1. Create study set");
            printMessage("2. List study sets");
            printMessage("3. Update study set");
            printMessage("4. Delete study set");
            printMessage("0. Back");

            try {
                switch (readLine("Choose: ")) {
                    case "1" -> createStudySet();
                    case "2" -> listStudySets();
                    case "3" -> updateStudySet();
                    case "4" -> deleteStudySet();
                    case "0" -> managing = false;
                    default -> printMessage("Invalid choice.");
                }
            } catch (IllegalArgumentException exception) {
                printMessage(exception.getMessage());
            }
        }
    }

    private void manageFlashcards() {
        boolean managing = true;
        while (managing) {
            printMessage("");
            printMessage("Flashcards");
            printMessage("1. Create flashcard");
            printMessage("2. List flashcards");
            printMessage("3. Update flashcard");
            printMessage("4. Delete flashcard");
            printMessage("0. Back");

            try {
                switch (readLine("Choose: ")) {
                    case "1" -> createFlashcard();
                    case "2" -> listFlashcards();
                    case "3" -> updateFlashcard();
                    case "4" -> deleteFlashcard();
                    case "0" -> managing = false;
                    default -> printMessage("Invalid choice.");
                }
            } catch (IllegalArgumentException exception) {
                printMessage(exception.getMessage());
            }
        }
    }

    private void createFolder() {
        Folder folder = folderService.createFolder(readLine("Folder name: "));
        printMessage("Created folder #" + folder.getId() + ".");
    }

    private void listFolders() {
        List<Folder> folders = folderService.listFolders();
        if (folders.isEmpty()) {
            printMessage("No folders yet.");
            return;
        }
        folders.forEach(folder -> printMessage(folder.getId() + ". " + folder.getName()));
    }

    private void updateFolder() {
        Long id = readLong("Folder id: ");
        Folder folder = folderService.updateFolder(id, readLine("New folder name: "));
        printMessage("Updated folder #" + folder.getId() + ".");
    }

    private void deleteFolder() {
        folderService.deleteFolder(readLong("Folder id: "));
        printMessage("Folder deleted if it existed.");
    }

    private void createStudySet() {
        Long folderId = readLong("Folder id: ");
        Folder folder = folderRepository.findByIdWithStudySets(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder was not found."));
        StudySet studySet = studySetService.createStudySet(folder, readLine("Study set title: "));
        printMessage("Created study set #" + studySet.getId() + ".");
    }

    private void listStudySets() {
        List<StudySet> studySets = studySetService.listStudySets();
        if (studySets.isEmpty()) {
            printMessage("No study sets yet.");
            return;
        }
        studySets.forEach(studySet -> printMessage(studySet.getId() + ". " + studySet.getTitle()));
    }

    private void updateStudySet() {
        Long id = readLong("Study set id: ");
        StudySet studySet = studySetService.updateStudySet(id, readLine("New title: "));
        printMessage("Updated study set #" + studySet.getId() + ".");
    }

    private void deleteStudySet() {
        studySetService.deleteStudySet(readLong("Study set id: "));
        printMessage("Study set deleted if it existed.");
    }

    private void createFlashcard() {
        Long studySetId = readLong("Study set id: ");
        StudySet studySet = studySetRepository.findByIdWithFlashcards(studySetId)
                .orElseThrow(() -> new IllegalArgumentException("Study set was not found."));
        Flashcard flashcard = flashcardService.createFlashcard(
                studySet,
                readLine("Term: "),
                readLine("Definition: ")
        );
        printMessage("Created flashcard #" + flashcard.getId() + ".");
    }

    private void listFlashcards() {
        List<Flashcard> flashcards = flashcardService.listFlashcards();
        if (flashcards.isEmpty()) {
            printMessage("No flashcards yet.");
            return;
        }
        flashcards.forEach(flashcard -> printMessage(
                flashcard.getId() + ". " + flashcard.getTerm() + " = " + flashcard.getDefinition()
        ));
    }

    private void updateFlashcard() {
        Long id = readLong("Flashcard id: ");
        Flashcard flashcard = flashcardService.updateFlashcard(
                id,
                readLine("New term: "),
                readLine("New definition: ")
        );
        printMessage("Updated flashcard #" + flashcard.getId() + ".");
    }

    private void deleteFlashcard() {
        flashcardService.deleteFlashcard(readLong("Flashcard id: "));
        printMessage("Flashcard deleted if it existed.");
    }

    private void viewStudySet() {
        Long id = readLong("Study set id: ");
        Optional<StudySet> studySetResult = studySetRepository.findByIdWithFlashcards(id);
        if (studySetResult.isEmpty()) {
            printMessage("Study set was not found.");
            return;
        }

        StudySet studySet = studySetResult.get();
        List<Flashcard> flashcards = flashcardRepository.findByStudySetId(id);

        printMessage("");
        printMessage(studySet.getTitle());
        if (flashcards.isEmpty()) {
            printMessage("No flashcards in this study set yet.");
            return;
        }
        for (int index = 0; index < flashcards.size(); index++) {
            Flashcard flashcard = flashcards.get(index);
            printMessage((index + 1) + ". " + flashcard.getTerm());
            printMessage("   " + flashcard.getDefinition());
        }
    }

    private void seedMockData() {
        MockDataService.SeedResult result = mockDataService.seed();
        if (result.foldersCreated() == 0) {
            printMessage("Mock data already exists. No new records were created.");
            return;
        }

        printMessage("Mock data created:");
        printMessage("- Folders: " + result.foldersCreated());
        printMessage("- Study sets: " + result.studySetsCreated());
        printMessage("- Flashcards: " + result.flashcardsCreated());
    }

    private Long readLong(String prompt) {
        String value = readLine(prompt);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Please enter a valid numeric id.");
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private void printMessage(String message) {
        System.out.println(message);
    }
}
