package com.wizeline.bookstore.dto;

/**
 * Data Transfer Object representing an outgoing HTTP response payload containing Book details.
 * Implemented using a modern Java 21 Record for immutability and thread safety.
 */
public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        Integer publishedYear,
        boolean available
) {
}
