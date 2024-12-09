package com.example.OnlineBookstore.controller;

import com.example.OnlineBookstore.model.Book;
import com.example.OnlineBookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book testBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testBook = new Book("Java Programming", "John Doe", 29.99, 10, "Programming");
    }

    @Test
    void testAddBook() throws Exception {
        when(bookService.addBook(any(Book.class))).thenReturn(testBook);

        mockMvc.perform(post("/api/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Programming"))
                .andExpect(jsonPath("$.author").value("John Doe"))
                .andExpect(jsonPath("$.price").value(29.99));

        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books/"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById_Found() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(testBook));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Programming"))
                .andExpect(jsonPath("$.author").value("John Doe"));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        when(bookService.getBookById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/2"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).getBookById(2L);
    }

    @Test
    void testUpdateBook() throws Exception {
        when(bookService.updateBook(1L, testBook)).thenReturn(testBook);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Programming"));

        verify(bookService, times(1)).updateBook(1L, testBook);
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(1L);
    }
}
