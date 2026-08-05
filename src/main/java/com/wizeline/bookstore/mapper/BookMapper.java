package com.wizeline.bookstore.mapper;

import com.wizeline.bookstore.dto.BookRequest;
import com.wizeline.bookstore.dto.BookResponse;
import com.wizeline.bookstore.entity.Book;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between domain entity {@link Book} and DTO objects.
 */
@Component
public class BookMapper {

    /**
     * Converts a {@link BookRequest} DTO into a new {@link Book} entity instance.
     */
    public Book toEntity(BookRequest request) {
        if (request == null) {
            return null;
        }
        return new Book(
                request.title(),
                request.author(),
                request.isbn(),
                request.publishedYear(),
                Boolean.TRUE.equals(request.available())
        );
    }

    /**
     * Updates an existing managed {@link Book} entity with state values from {@link BookRequest}.
     */
    public void updateEntityFromRequest(BookRequest request, Book entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setTitle(request.title());
        entity.setAuthor(request.author());
        entity.setIsbn(request.isbn());
        entity.setPublishedYear(request.publishedYear());
        if (request.available() != null) {
            entity.setAvailable(request.available());
        }
    }

    /**
     * Converts a {@link Book} entity instance into a {@link BookResponse} DTO.
     */
    public BookResponse toResponse(Book entity) {
        if (entity == null) {
            return null;
        }
        return new BookResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getIsbn(),
                entity.getPublishedYear(),
                entity.isAvailable()
        );
    }
}
