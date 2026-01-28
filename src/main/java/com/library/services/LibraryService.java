package com.library.services;

import com.library.exceptions.*;
import com.library.models.Author;
import com.library.models.Book;
import com.library.models.Loan;
import com.library.repositories.AuthorRepository;
import com.library.repositories.BookRepository;
import com.library.repositories.LoanRepository;

import java.util.List;

public class LibraryService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public LibraryService(
            AuthorRepository authorRepository,
            BookRepository bookRepository,
            LoanRepository loanRepository
    ) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findManyByAvailability();
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(int id) {
        Author author = authorRepository.findById(id);

        if (author == null) {
            throw new AuthorNotFoundException();
        }

        return author;
    }

    public Author createAuthor(String name) {
        Author author = new Author(name);

        if (authorRepository.findByName(author.getName()) != null) {
            throw new AuthorAlreadyExistsException();
        }

        authorRepository.create(author);

        return author;
    }

    public void updateAuthor(Author author, String name) {
        if (!author.getName().equals(name)) {
            author.changeName(name);

            if (authorRepository.findByName(author.getName()) != null) {
                throw new AuthorAlreadyExistsException();
            }
        }

        authorRepository.save(author);
    }

    public Author deleteAuthor(int id) {
        Author author = authorRepository.findById(id);

        if (author == null) {
            throw new AuthorNotFoundException();
        }

        authorRepository.delete(id);

        return author;
    }

    public List<Author> searchAuthorsByName(String name) {
        return authorRepository.findManyByName(name);
    }

    public void borrowBook(int bookId, String customerName) {
        Book book = bookRepository.findById(bookId);

        if (book == null) {
            throw new BookNotFoundException();
        }

        if (!book.isAvailable()) {
            throw new BookAlreadyBorrowedException();
        }

        book.markAsBorrowed();
        bookRepository.save(book);

        Loan loan = new Loan(book, customerName);
        loanRepository.create(loan);
    }

    public void returnBook(int bookId) {
        Book book = bookRepository.findById(bookId);

        if (book == null) {
            throw new BookNotFoundException();
        }

        if (book.isAvailable()) {
            throw new BookNotBorrowedException();
        }

        Loan loan = loanRepository.findByBookId(book.getId());

        if (loan == null) {
            throw new LoanNotFoundException();
        }

        book.markAsReturned();
        bookRepository.save(book);

        loan.markAsReturned();
        loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getActiveLoans() {
        return loanRepository.findManyByReturnDateIsNull();
    }
}
