package com.library.models;

import java.time.LocalDateTime;

public class Book {
    private int id;
    private String title;
    private Author author;
    private boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Book() {
    }

    public Book(String title, Author author) {
        validateTitle(title);

        this.title = title;
        this.author = author;
        this.available = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsBorrowed() {
        this.available = false;
        touch();
    }

    public void markAsReturned() {
        this.available = true;
        touch();
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Book title cannot be empty");
        }

        if (title.length() < 3) {
            throw new IllegalArgumentException("Book title must have at least 3 characters");
        }

        if (title.length() > 100) {
            throw new IllegalArgumentException("Book title must have at most 100 characters");
        }
    }

    public void changeTitle(String title) {
        validateTitle(title);
        this.title = title;
        touch();
    }

    public void changeAuthor(Author author) {
        this.author = author;
        touch();
    }
}
