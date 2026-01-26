package com.library.models;

import java.time.LocalDateTime;

public class Book {
    private int id;
    private String title;
    private Author author;
    private boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Book(int id, String title, Author author) {
        this.id = id;
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

    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setAvailable(boolean available) {
        this.available = available;
        touch();
    }


}
