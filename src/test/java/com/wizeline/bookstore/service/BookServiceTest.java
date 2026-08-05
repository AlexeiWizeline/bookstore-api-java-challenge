package com.wizeline.bookstore.service;

import com.wizeline.bookstore.dto.BookRequest;
import com.wizeline.bookstore.dto.BookResponse;
import com.wizeline.bookstore.entity.Book;
import com.wizeline.bookstore.exception.ResourceNotFoundException;
import com.wizeline.bookstore.mapper.BookMapper;
import com.wizeline.bookstore.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Should return all books mapped to responses")
    void getAllBooks_returnsMappedResponses() {
        Book book = new Book(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true);
        BookResponse response = new BookResponse(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true);

        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toResponse(book)).thenReturn(response);

        List<BookResponse> result = bookService.getAllBooks();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().title()).isEqualTo("Clean Code");
        verify(bookRepository).findAll();
    }

    @Test
    @DisplayName("Should return book by ID when found")
    void getBookById_success() {
        Book book = new Book(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true);
        BookResponse response = new BookResponse(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toResponse(book)).thenReturn(response);

        BookResponse result = bookService.getBookById(1L);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("Clean Code");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when book ID does not exist")
    void getBookById_notFound_throwsResourceNotFoundException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBookById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book not found with id: 99");
    }

    @Test
    @DisplayName("Should create book successfully when ISBN is unique")
    void createBook_success() {
        BookRequest request = new BookRequest("Clean Code", "Robert C. Martin", "9780132350884", 2008, true);
        Book book = new Book(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true);
        BookResponse response = new BookResponse(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true);

        when(bookRepository.existsByIsbn("9780132350884")).thenReturn(false);
        when(bookMapper.toEntity(request)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponse(book)).thenReturn(response);

        BookResponse result = bookService.createBook(request);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("Should delete book by ID when found")
    void deleteBook_success() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.deleteBook(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existing book ID")
    void deleteBook_notFound_throwsResourceNotFoundException() {
        when(bookRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> bookService.deleteBook(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book not found with id: 99");

        verify(bookRepository, never()).deleteById(any());
    }
}
