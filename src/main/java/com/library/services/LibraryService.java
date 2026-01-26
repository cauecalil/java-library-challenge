package com.library.services;

import com.library.models.Author;
import com.library.models.Book;
import com.library.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private List<Book> books = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();
}
