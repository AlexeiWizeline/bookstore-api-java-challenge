package com.wizeline.bookstore.repository;

import com.wizeline.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository for managing {@link Book} entity persistence operations.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Check if a book exists by its unique ISBN.
     *
     * @param isbn the book ISBN to check
     * @return true if a book exists with the given ISBN, false otherwise
     */
    boolean existsByIsbn(String isbn);

    /**
     * Find a book by its unique ISBN.
     *
     * @param isbn the book ISBN to search
     * @return an {@link Optional} containing the found Book, or empty if not found
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Check if another book exists with the given ISBN excluding a specific book ID.
     * Useful for validation during PUT (update) operations.
     *
     * @param isbn the book ISBN
     * @param id the book ID to exclude from match
     * @return true if another book with the same ISBN exists, false otherwise
     */
    boolean existsByIsbnAndIdNot(String isbn, Long id);
}
