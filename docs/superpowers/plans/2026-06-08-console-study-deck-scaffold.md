# Console Study Deck Scaffold Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Scaffold a Maven Hibernate JPA MySQL console CRUD app for folders, study sets, and flashcards.

**Architecture:** Entities model the study domain, repositories isolate JPA persistence, services enforce simple validation, and console classes handle user interaction. The first build avoids web/API concerns and keeps learning/test algorithms out of scope.

**Tech Stack:** Java 17, Maven, Hibernate ORM 6, Jakarta Persistence, MySQL Connector/J, Lombok, JUnit Jupiter.

---

### Task 1: Project Configuration And Tests

**Files:**
- Modify: `pom.xml`
- Delete: `src/main/java/org/example/App.java`
- Delete: `src/test/java/org/example/AppTest.java`
- Create: `src/test/java/com/studydeck/service/FolderServiceTest.java`
- Create: `src/test/java/com/studydeck/service/StudySetServiceTest.java`
- Create: `src/test/java/com/studydeck/service/FlashcardServiceTest.java`

- [x] Configure Maven dependencies and compiler plugins.
- [x] Replace generated sample code with service tests.
- [ ] Run tests and verify they fail because production classes do not exist.

### Task 2: Domain Model

**Files:**
- Create: `src/main/java/com/studydeck/entity/Folder.java`
- Create: `src/main/java/com/studydeck/entity/StudySet.java`
- Create: `src/main/java/com/studydeck/entity/Flashcard.java`

- [ ] Implement JPA entities with Lombok and helper methods for parent-child relationships.
- [ ] Run service tests and verify the remaining failures are service/repository related.

### Task 3: Repositories And Services

**Files:**
- Create: `src/main/java/com/studydeck/repository/CrudRepository.java`
- Create: `src/main/java/com/studydeck/repository/JpaRepository.java`
- Create: `src/main/java/com/studydeck/repository/FolderRepository.java`
- Create: `src/main/java/com/studydeck/repository/StudySetRepository.java`
- Create: `src/main/java/com/studydeck/repository/FlashcardRepository.java`
- Create: `src/main/java/com/studydeck/service/FolderService.java`
- Create: `src/main/java/com/studydeck/service/StudySetService.java`
- Create: `src/main/java/com/studydeck/service/FlashcardService.java`

- [ ] Implement repository CRUD and service validation.
- [ ] Run tests and verify service behavior passes.

### Task 4: Hibernate Config And Console UI

**Files:**
- Create: `src/main/resources/META-INF/persistence.xml`
- Create: `src/main/java/com/studydeck/config/JpaUtil.java`
- Create: `src/main/java/com/studydeck/App.java`
- Create: `src/main/java/com/studydeck/console/ConsoleApp.java`

- [ ] Add persistence config with `${DB_URL}`, `${DB_USER}`, and `${DB_PASSWORD}` placeholders.
- [ ] Add console menus for CRUD and study set viewing.
- [ ] Run `mvn test` and `mvn -DskipTests package`.
