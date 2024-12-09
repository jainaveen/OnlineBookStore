package com.example.OnlineBookstore.service;

import com.example.OnlineBookstore.model.Book;
import com.example.OnlineBookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public Book updateBook(Long bookId, Book updatedBook) {
        if (bookRepository.existsById(bookId)) {
            updatedBook.setId(bookId);
            return bookRepository.save(updatedBook);
        }
        return null;
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
