package com.library.services;

import com.library.exceptions.BookAlreadyBorrowedException;
import com.library.exceptions.BookNotBorrowedException;
import com.library.exceptions.BookNotFoundException;
import com.library.exceptions.LoanNotFoundException;
import com.library.models.Book;
import com.library.models.Loan;
import com.library.repositories.BookRepository;
import com.library.repositories.LoanRepository;

import java.util.List;

public class LibraryService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public LibraryService(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findManyByAvailability();
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
