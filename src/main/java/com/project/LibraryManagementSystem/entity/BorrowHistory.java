package com.project.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrow_history")
public class BorrowHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
    private LocalDateTime returnedAt;

    private String status;
    // PENDING, APPROVED, RETURN_REQUESTED, RETURNED


    public BorrowHistory(Long id) {
        this.id = id;
    }

    public BorrowHistory(User user, Book book, LocalDateTime requestedAt, LocalDateTime approvedAt, LocalDateTime returnedAt, String status) {
        this.user = user;
        this.book = book;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.returnedAt = returnedAt;
        this.status = status;
    }

    public BorrowHistory() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
