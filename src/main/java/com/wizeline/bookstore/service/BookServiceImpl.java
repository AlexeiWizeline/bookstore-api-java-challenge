package com.wizeline.bookstore.service;

import com.wizeline.bookstore.dto.BookRequest;
import com.wizeline.bookstore.dto.BookResponse;
import com.wizeline.bookstore.entity.Book;
import com.wizeline.bookstore.exception.ResourceNotFoundException;
import com.wizeline.bookstore.mapper.BookMapper;
import com.wizeline.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link BookService} containing core business logic and transaction boundaries.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    /**
     * Constructor Injection enforcing explicit dependency declaration.
     */
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        Book book = findBookOrThrow(id);
        return bookMapper.toResponse(book);
    }

    @Override
    @Transactional
    public BookResponse createBook(BookRequest request) {
        Book bookEntity = bookMapper.toEntity(request);
        Book savedBook = bookRepository.save(bookEntity);
        return bookMapper.toResponse(savedBook);
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long id, BookRequest request) {
        Book existingBook = findBookOrThrow(id);

        bookMapper.updateEntityFromRequest(request, existingBook);
        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toResponse(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }
}
