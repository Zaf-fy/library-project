package com.project.LibraryManagementSystem.JpaRepository;

import com.project.LibraryManagementSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContaining(String title);
}
