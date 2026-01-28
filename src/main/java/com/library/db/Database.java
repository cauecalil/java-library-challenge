package com.library.db;

import org.jdbi.v3.core.Jdbi;

import java.time.LocalDateTime;

public class Database {
    private static Jdbi db;

    public static Jdbi get() {
        if (db == null) {
            db = Jdbi.create("jdbc:sqlite:library.db?foreign_keys=on");
            seed();
        }
        return db;
    }

    private static void seed() {
        db.useHandle(handle -> {
            handle.execute("PRAGMA foreign_keys = ON");

            handle.execute("""
                CREATE TABLE IF NOT EXISTS authors (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL
                );
            """);

            handle.execute("""
                INSERT OR IGNORE INTO authors (id, name)
                VALUES
                (0, 'Unknown'),
                (1, 'J.R.R. Tolkien'),
                (2, 'George R.R. Martin');
            """);

            handle.execute("""
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    author_id INTEGER NOT NULL,
                    available INTEGER NOT NULL,
                    created_at TEXT NOT NULL,
                    updated_at TEXT NOT NULL,
                    FOREIGN KEY(author_id) REFERENCES authors(id) ON UPDATE CASCADE ON DELETE RESTRICT
                );
            """);

            handle.createUpdate("""
                INSERT OR IGNORE INTO books (id, title, author_id, available, created_at, updated_at)
                VALUES
                (1, 'The Hobbit', 1, 1, :dateTimeNow, :dateTimeNow),
                (2, 'The Lord of the Rings', 1, 1, :dateTimeNow, :dateTimeNow),
                (3, 'A Game of Thrones', 2, 1, :dateTimeNow, :dateTimeNow),
                (4, 'A Dance with Dragons', 2, 1, :dateTimeNow, :dateTimeNow);
            """).bind("dateTimeNow", LocalDateTime.now().toString()).execute();

            handle.execute("""
                CREATE TABLE IF NOT EXISTS loans (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    book_id INTEGER NOT NULL,
                    customer_name TEXT NOT NULL,
                    loan_date TEXT NOT NULL,
                    return_date TEXT,
                    FOREIGN KEY(book_id) REFERENCES books(id) ON UPDATE CASCADE ON DELETE CASCADE
                );
            """);
        });
    }
}