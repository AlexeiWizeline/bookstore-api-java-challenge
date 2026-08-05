package com.wizeline.bookstore.exception;

/**
 * Exception thrown when attempting to create or update a book with an ISBN that already exists.
 */
public class DuplicateIsbnException extends RuntimeException {

    public DuplicateIsbnException(String message) {
        super(message);
    }
}
