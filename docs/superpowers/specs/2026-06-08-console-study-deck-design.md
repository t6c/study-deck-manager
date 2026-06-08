# Console Study Deck Manager Design

## Goal

Build a beginner-friendly console CRUD application inspired by Knowt and Quizlet. The first version manages folders, study sets, and flashcards only. Learning, practice tests, spaced repetition, APIs, and web UI are intentionally out of scope.

## Architecture

The application uses a simple three-layer design:

- Entity layer: JPA entities for `Folder`, `StudySet`, and `Flashcard`.
- Repository layer: CRUD persistence classes backed by Jakarta Persistence `EntityManager`.
- Service layer: validation and business operations used by the console UI.
- Console layer: menu-based input and output.

## Data Model

`Folder` represents a user material folder. It has many `StudySet` records.

`StudySet` represents a file/material inside a folder. It has many `Flashcard` records.

`Flashcard` represents one term and definition inside a study set.

Deleting a folder cascades to its study sets and flashcards. Deleting a study set cascades to its flashcards.

## Configuration

Hibernate JPA is configured through `src/main/resources/META-INF/persistence.xml`. Database credentials use property placeholders such as `${DB_PASSWORD}` so each developer can provide values through IntelliJ environment variables, VM options, or local shell variables.

## First Console Features

The main menu exposes folder CRUD, study set CRUD, flashcard CRUD, and a read-only view that prints one study set with all flashcards. The app is intentionally synchronous and local-only.

## Testing

Unit tests cover service-layer validation and entity relationship behavior. Repository behavior is prepared for MySQL but not tested against a live database in the first scaffold because each developer may have different local MySQL credentials.
