package com.studydeck.service;

import com.studydeck.entity.Folder;
import com.studydeck.entity.StudySet;

import java.util.List;

public class MockDataService {

    private static final String MOCK_FOLDER_PREFIX = "[Mock]";

    private final FolderService folderService;
    private final StudySetService studySetService;
    private final FlashcardService flashcardService;

    public MockDataService(
            FolderService folderService,
            StudySetService studySetService,
            FlashcardService flashcardService
    ) {
        this.folderService = folderService;
        this.studySetService = studySetService;
        this.flashcardService = flashcardService;
    }

    public SeedResult seed() {
        boolean alreadySeeded = folderService.listFolders().stream()
                .anyMatch(folder -> folder.getName().startsWith(MOCK_FOLDER_PREFIX));
        if (alreadySeeded) {
            return new SeedResult(0, 0, 0);
        }

        SeedCounter counter = new SeedCounter();
        seedFolder(
                "[Mock] English For Vietnamese Learners",
                List.of(
                        new StudySetSeed(
                                "Daily English Vocabulary",
                                List.of(
                                        card("Reliable", "Đáng tin cậy", "A reliable teammate finishes work on time."),
                                        card("Improve", "Cải thiện", "We improve our English by practicing every day."),
                                        card("Schedule", "Lịch trình", "My study schedule starts at 8 PM."),
                                        card("Confident", "Tự tin", "She feels confident before the presentation."),
                                        card("Explain", "Giải thích", "Can you explain this word in Vietnamese?"),
                                        card("Requirement", "Yêu cầu", "The project requirement is simple CRUD."),
                                        card("Deadline", "Hạn chót", "The assignment deadline is Friday.")
                                )
                        ),
                        new StudySetSeed(
                                "Common English Phrases",
                                List.of(
                                        card("How does it work?", "Nó hoạt động như thế nào?", "Use this when asking about logic or process."),
                                        card("Could you repeat that?", "Bạn có thể nhắc lại không?", "Useful when you did not hear clearly."),
                                        card("I agree with you.", "Tôi đồng ý với bạn.", "A polite way to show agreement."),
                                        card("That makes sense.", "Điều đó hợp lý.", "Use this when you understand an explanation."),
                                        card("Let me check first.", "Để tôi kiểm tra trước.", "Useful at work before answering."),
                                        card("What do you mean?", "Ý bạn là gì?", "Ask this when something is unclear."),
                                        card("I need more practice.", "Tôi cần luyện tập thêm.", "A natural sentence for learners.")
                                )
                        )
                ),
                counter
        );

        seedFolder(
                "[Mock] Java Interview Practice",
                List.of(
                        new StudySetSeed(
                                "Core Java Questions",
                                List.of(
                                        card("What is OOP?", "Object-Oriented Programming", "A style based on objects, classes, encapsulation, inheritance, and polymorphism."),
                                        card("What is encapsulation?", "Wrapping data and behavior together", "Fields are usually private and accessed through methods."),
                                        card("What is inheritance?", "Reusing behavior from a parent class", "A child class can extend a parent class."),
                                        card("What is polymorphism?", "One interface, many implementations", "A method call can behave differently depending on the object."),
                                        card("What is an interface?", "A contract for behavior", "Classes implement interfaces to promise certain methods."),
                                        card("What is a constructor?", "Code used to create an object", "It runs when a new object is created."),
                                        card("What is final?", "A keyword that prevents change", "It can stop reassignment, overriding, or inheritance depending on where it is used.")
                                )
                        ),
                        new StudySetSeed(
                                "Collections Basics",
                                List.of(
                                        card("List", "Ordered collection", "Allows duplicates and keeps insertion order."),
                                        card("Set", "Unique collection", "Does not allow duplicate elements."),
                                        card("Map", "Key-value collection", "Stores values by unique keys."),
                                        card("ArrayList", "Resizable array implementation", "Fast for reading by index."),
                                        card("HashSet", "Hash-based Set implementation", "Useful when you only need unique values."),
                                        card("HashMap", "Hash-based Map implementation", "Common choice for key-value lookup."),
                                        card("Iterator", "Object used to loop through a collection", "It provides methods such as hasNext and next.")
                                )
                        )
                ),
                counter
        );

        seedFolder(
                "[Mock] Hibernate JPA Basics",
                List.of(
                        new StudySetSeed(
                                "JPA And Hibernate",
                                List.of(
                                        card("Entity", "A Java class mapped to a database table", "JPA uses @Entity to mark it."),
                                        card("Id", "Primary key field", "JPA uses @Id for the identifier."),
                                        card("EntityManager", "Main JPA object for database work", "It can persist, find, merge, and remove entities."),
                                        card("Persistence Unit", "Named JPA configuration", "It is declared in persistence.xml."),
                                        card("OneToMany", "Relationship from one parent to many children", "Folder to study sets is one example."),
                                        card("ManyToOne", "Relationship from many children to one parent", "Many flashcards belong to one study set."),
                                        card("Cascade", "Operation propagation between related entities", "Deleting a folder can delete its study sets.")
                                )
                        ),
                        new StudySetSeed(
                                "SQL Terms",
                                List.of(
                                        card("Table", "A database structure with rows and columns", "Each entity usually maps to one table."),
                                        card("Row", "One record in a table", "One flashcard row stores one term and definition."),
                                        card("Column", "A named field in a table", "term and definition are columns."),
                                        card("Primary Key", "Unique row identifier", "The id column is the primary key."),
                                        card("Foreign Key", "Column that points to another table", "study_set_id links flashcards to study sets."),
                                        card("CRUD", "Create, Read, Update, Delete", "The basic operations in this console app."),
                                        card("Transaction", "A safe unit of database work", "Commit saves changes; rollback cancels changes.")
                                )
                        )
                ),
                counter
        );

        return new SeedResult(counter.folders, counter.studySets, counter.flashcards);
    }

    private void seedFolder(String folderName, List<StudySetSeed> studySets, SeedCounter counter) {
        Folder folder = folderService.createFolder(folderName);
        counter.folders++;

        for (StudySetSeed studySetSeed : studySets) {
            StudySet studySet = studySetService.createStudySet(folder, studySetSeed.title());
            counter.studySets++;

            for (FlashcardSeed flashcardSeed : studySetSeed.flashcards()) {
                flashcardService.createFlashcard(
                        studySet,
                        flashcardSeed.term(),
                        flashcardSeed.definition()
                );
                counter.flashcards++;
            }
        }
    }

    private static FlashcardSeed card(String term, String definition, String example) {
        return new FlashcardSeed(term, definition + " - " + example);
    }

    public record SeedResult(int foldersCreated, int studySetsCreated, int flashcardsCreated) {
    }

    private record StudySetSeed(String title, List<FlashcardSeed> flashcards) {
    }

    private record FlashcardSeed(String term, String definition) {
    }

    private static final class SeedCounter {
        private int folders;
        private int studySets;
        private int flashcards;
    }
}
