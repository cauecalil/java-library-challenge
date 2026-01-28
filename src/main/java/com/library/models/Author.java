package com.library.models;

import java.time.LocalDate;

public class Author {
    private int id;
    private String name;

    public Author() {
    }

    public Author(String name) {
        validateName(name);

        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Author name cannot be empty");
        }

        if (name.length() < 3) {
            throw new IllegalArgumentException("Author name must have at least 3 characters");
        }

        if (name.length() > 100) {
            throw new IllegalArgumentException("Author name must have at most 100 characters");
        }
    }

    public void changeName(String name) {
        validateName(name);

        this.name = name;
    }
}
