package com.wizeline.bookstore.service;

import com.wizeline.bookstore.dto.BookRequest;
import com.wizeline.bookstore.dto.BookResponse;

import java.util.List;

/**
 * Service interface defining business operations for managing Books.
 */
public interface BookService {

    /**
     * Retrieve all books stored in the bookstore system.
     *
     * @return list of {@link BookResponse} DTOs
     */
    List<BookResponse> getAllBooks();

    /**
     * Retrieve a specific book by its unique database ID.
     *
     * @param id the book ID
     * @return the corresponding {@link BookResponse}
     */
    BookResponse getBookById(Long id);

    /**
     * Create and persist a new book.
     *
     * @param request the book request payload
     * @return the created {@link BookResponse}
     */
    BookResponse createBook(BookRequest request);

    /**
     * Update an existing book by its ID.
     *
     * @param id the book ID to update
     * @param request the updated book payload
     * @return the updated {@link BookResponse}
     */
    BookResponse updateBook(Long id, BookRequest request);

    /**
     * Delete a book by its database ID.
     *
     * @param id the book ID to delete
     */
    void deleteBook(Long id);
}
