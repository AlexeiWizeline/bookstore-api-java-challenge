package com.wizeline.bookstore.repository;

import com.wizeline.bookstore.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Should save and find book by ID")
    void testSaveAndFindById() {
        Book book = new Book("Domain-Driven Design", "Eric Evans", "9789999999991", 2003, true);
        Book savedBook = entityManager.persistAndFlush(book);

        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Domain-Driven Design");
        assertThat(foundBook.get().getIsbn()).isEqualTo("9789999999991");
    }
}
