package com.library.models;

import java.time.LocalDate;

public class Author {
    private int id;
    private String name;
    private LocalDate birthDate;

    public Author(int id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }
}
