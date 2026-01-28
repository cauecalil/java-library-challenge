package com.library.exceptions;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException() {
        super("Author was not found in the system.");
    }
}
