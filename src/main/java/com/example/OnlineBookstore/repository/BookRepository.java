package com.example.OnlineBookstore.repository;
import com.example.OnlineBookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Custom query methods can be added here if needed
}