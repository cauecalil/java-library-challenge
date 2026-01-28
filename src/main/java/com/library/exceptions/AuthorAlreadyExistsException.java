package com.library.exceptions;

public class AuthorAlreadyExistsException extends RuntimeException {
    public AuthorAlreadyExistsException() {
        super("An author with this name already exists.");
    }
}
