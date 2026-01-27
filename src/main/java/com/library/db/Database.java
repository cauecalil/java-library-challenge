package com.library.db;

import org.jdbi.v3.core.Jdbi;

import java.time.LocalDateTime;

public class Database {
    private static Jdbi db;

    public static Jdbi get() {
        if (db == null) {
            db = Jdbi.create("jdbc:sqlite:library.db");
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
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    author_id INTEGER NOT NULL,
                    available INTEGER NOT NULL,
                    created_at TEXT NOT NULL,
                    updated_at TEXT NOT NULL,
                    FOREIGN KEY(author_id) REFERENCES authors(id)
                );
            """);

            handle.execute("""
                CREATE TABLE IF NOT EXISTS loans (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    book_id INTEGER NOT NULL,
                    customer_name TEXT NOT NULL,
                    loan_date TEXT NOT NULL,
                    return_date TEXT DEFAULT NULL,
                    FOREIGN KEY(book_id) REFERENCES books(id)
                );
            """);

            long authorsCount = handle
                    .createQuery("SELECT COUNT(*) FROM authors")
                    .mapTo(Long.class)
                    .one();

            if (authorsCount == 0) {
                handle.execute("""
                    INSERT INTO authors (name) VALUES
                    ('J.R.R. Tolkien'),
                    ('George R.R. Martin');
                """);
            }

            long booksCount = handle
                    .createQuery("SELECT COUNT(*) FROM books")
                    .mapTo(Long.class)
                    .one();

            if (booksCount == 0) {
                handle.createUpdate("""
                    INSERT INTO books (title, author_id, available, created_at, updated_at)
                    VALUES
                    ('The Hobbit', 1, 1, :dateTimeNow, :dateTimeNow),
                    ('The Lord of the Rings', 1, 1, :dateTimeNow, :dateTimeNow),
                    ('A Game of Thrones', 2, 1, :dateTimeNow, :dateTimeNow),
                    ('A Dance with Dragons', 2, 1, :dateTimeNow, :dateTimeNow);
                """).bind("dateTimeNow", LocalDateTime.now().toString()).execute();
            }
        });
    }
}