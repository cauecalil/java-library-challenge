package com.library.services;

import com.library.exceptions.BookAlreadyBorrowedException;
import com.library.exceptions.BookNotBorrowedException;
import com.library.exceptions.BookNotFoundException;
import com.library.models.Author;
import com.library.models.Book;
import com.library.models.Loan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private List<Book> books = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    public LibraryService() {
        seed();
    }

    private void seed() {
        Author author1 = new Author(1, "J.R.R. Tolkien", LocalDate.of(1892, 1, 3));
        Author author2 = new Author(2, "George R.R. Martin", LocalDate.of(1948, 9, 20));

        authors.add(author1);
        authors.add(author2);

        books.add(new Book(1, "The Hobbit", author1));
        books.add(new Book(2, "The Fellowship of the Ring", author1));
        books.add(new Book(3, "A Game of Thrones", author2));
        books.add(new Book(4, "A Dance with Dragons", author2));
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }

        return availableBooks;
    }

    public void borrowBook(int bookId, String customerName) {
        Book book = null;

        for (Book _book : books) {
            if (_book.getId() == bookId) {
                book = _book;
                break;
            }
        }

        if (book == null) {
            throw new BookNotFoundException();
        }

        if (!book.isAvailable()) {
            throw new BookAlreadyBorrowedException();
        }

        book.setAvailable(false);
    }

    public void returnBook(int bookId) {
        Book book = null;

        for (Book _book : books) {
            if (_book.getId() == bookId) {
                book = _book;
                break;
            }
        }

        if (book == null) {
            throw new BookNotFoundException();
        }

        if (book.isAvailable()) {
            throw new BookNotBorrowedException();
        }

        book.setAvailable(true);
    }
}
