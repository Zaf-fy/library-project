package com.project.LibraryManagementSystem.service;

import com.project.LibraryManagementSystem.entity.Book;

import java.util.List;

public interface BookService {

    Book addBook(Book book);

    public Book getBookById(Long id);

    public void updateBook(Book book);

    public void deleteBook(Long id);

    List<Book> getAllBooks();

    List<Book> searchBooks(String title);
}
