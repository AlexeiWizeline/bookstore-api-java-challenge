package com.wizeline.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.bookstore.dto.BookRequest;
import com.wizeline.bookstore.dto.BookResponse;
import com.wizeline.bookstore.exception.GlobalExceptionHandler;
import com.wizeline.bookstore.exception.ResourceNotFoundException;
import com.wizeline.bookstore.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(GlobalExceptionHandler.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    @Test
    @DisplayName("GET /books should return 200 OK and list of books")
    void getAllBooks_returnsOkStatusAndJsonList() throws Exception {
        BookResponse book = new BookResponse(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true);
        when(bookService.getAllBooks()).thenReturn(List.of(book));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Clean Code"))
                .andExpect(jsonPath("$[0].isbn").value("9780132350884"));
    }

    @Test
    @DisplayName("GET /books/{id} should return 200 OK when found")
    void getBookById_success_returnsOkStatus() throws Exception {
        BookResponse book = new BookResponse(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true);
        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    @DisplayName("GET /books/{id} should return 404 NOT FOUND when resource missing")
    void getBookById_notFound_returns404Status() throws Exception {
        when(bookService.getBookById(99L)).thenThrow(new ResourceNotFoundException("Book not found with id: 99"));

        mockMvc.perform(get("/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Book not found with id: 99"));
    }

    @Test
    @DisplayName("POST /books should return 201 CREATED for valid payload")
    void createBook_validPayload_returns201Status() throws Exception {
        BookRequest request = new BookRequest("Clean Code", "Robert C. Martin", "9780132350884", 2008, true);
        BookResponse response = new BookResponse(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true);

        when(bookService.createBook(any(BookRequest.class))).thenReturn(response);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    @DisplayName("POST /books should return 400 BAD REQUEST when validation fails")
    void createBook_invalidPayload_returns400Status() throws Exception {
        // Blank title and invalid ISBN format
        BookRequest invalidRequest = new BookRequest("", "Robert C. Martin", "invalid-isbn", 2008, true);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.validationErrors.title").exists())
                .andExpect(jsonPath("$.validationErrors.isbn").exists());
    }

    @Test
    @DisplayName("PUT /books/{id} should return 200 OK when updated")
    void updateBook_validPayload_returnsOkStatus() throws Exception {
        BookRequest request = new BookRequest("Clean Code 2nd Ed", "Robert C. Martin", "9780132350884", 2008, true);
        BookResponse response = new BookResponse(1L, "Clean Code 2nd Ed", "Robert C. Martin", "9780132350884", 2008, true);

        when(bookService.updateBook(eq(1L), any(BookRequest.class))).thenReturn(response);

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code 2nd Ed"));
    }

    @Test
    @DisplayName("DELETE /books/{id} should return 204 NO CONTENT on success")
    void deleteBook_success_returns204Status() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }
}
