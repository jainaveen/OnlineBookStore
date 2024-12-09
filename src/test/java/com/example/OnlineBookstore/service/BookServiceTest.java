package com.example.OnlineBookstore.service;
import com.example.OnlineBookstore.model.Book;
import com.example.OnlineBookstore.repository.BookRepository;
import com.example.OnlineBookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testAddBook() {
        Book book = new Book("Java Programming", "John Doe", 29.99, 10, "Programming");
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.addBook(book);

        assertNotNull(savedBook);
        assertEquals("Java Programming", savedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(
                new Book("Java Programming", "John Doe", 29.99, 10, "Programming"),
                new Book("Spring Framework", "Jane Doe", 39.99, 15, "Programming")
        );
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> retrievedBooks = bookService.getAllBooks();

        assertEquals(2, retrievedBooks.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById_Found() {
        Long bookId = 1L;
        Book book = new Book("Java Programming", "John Doe", 29.99, 10, "Programming");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<Book> retrievedBook = bookService.getBookById(bookId);

        assertTrue(retrievedBook.isPresent());
        assertEquals("Java Programming", retrievedBook.get().getTitle());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void testGetBookById_NotFound() {
        Long bookId = 2L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Optional<Book> retrievedBook = bookService.getBookById(bookId);

        assertFalse(retrievedBook.isPresent());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void testUpdateBook_Success() {
        Long bookId = 1L;
        Book existingBook = new Book("Old Book", "Jane Doe", 20.00, 5, "Fiction");
        Book updatedBook = new Book("Updated Book", "John Doe", 25.00, 8, "Fiction");

        when(bookRepository.existsById(bookId)).thenReturn(true);
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        Book result = bookService.updateBook(bookId, updatedBook);

        assertNotNull(result);
        assertEquals("Updated Book", result.getTitle());
        verify(bookRepository, times(1)).existsById(bookId);
        verify(bookRepository, times(1)).save(updatedBook);
    }

    @Test
    void testUpdateBook_NotFound() {
        Long bookId = 2L;
        Book updatedBook = new Book("Nonexistent Book", "Author", 15.00, 3, "Genre");

        when(bookRepository.existsById(bookId)).thenReturn(false);

        Book result = bookService.updateBook(bookId, updatedBook);

        assertNull(result);
        verify(bookRepository, times(1)).existsById(bookId);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;

        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }
}
