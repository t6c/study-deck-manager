# Study Deck Manager

A simple console CRUD app for managing study folders, study sets, and flashcards.

## Requirements

- Java 17
- Maven
- Docker Desktop

## Start MySQL

```bash
docker compose up -d
```

This starts MySQL on `localhost:3306` and creates this database:

```text
study_deck_manager
```

Default app database settings:

```text
DB_URL=jdbc:mysql://localhost:3306/study_deck_manager
DB_USER=root
DB_PASSWORD=
```

## Run The App

From terminal:

```bash
mvn exec:java
```

Main menu options:

```text
1. Manage folders
2. Manage study sets
3. Manage flashcards
4. View study set
5. Inject mock data
0. Exit
```

Use option `5` once after starting the app if you want sample folders, study sets, and flashcards for testing CRUD.

Or in IntelliJ:

1. Open the project as a Maven project.
2. Open `src/main/java/com/studydeck/App.java`.
3. Run the `main` method.

## Run Tests

```bash
mvn test
```

## Useful Docker Commands

Stop MySQL:

```bash
docker compose down
```

Stop MySQL and delete database data:

```bash
docker compose down -v
```

## If You Want A MySQL Password

Change `docker-compose.yml`:

```yaml
MYSQL_ROOT_PASSWORD: your_password
```

Remove this line:

```yaml
MYSQL_ALLOW_EMPTY_PASSWORD: "yes"
```

Then set this IntelliJ environment variable:

```text
DB_PASSWORD=your_password
```
