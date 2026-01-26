package com.library.exceptions;

public class BookNotBorrowedException extends RuntimeException {
    public BookNotBorrowedException() {
        super("The book was not marked as borrowed.");
    }
}
