package com.library.exceptions;

public class AuthorHasBooksException extends RuntimeException {
    public AuthorHasBooksException() {
        super("Cannot delete author because there are books linked to this author.");
    }
}