package com.project.LibraryManagementSystem.service;

import com.project.LibraryManagementSystem.entity.BorrowHistory;

import java.util.List;

public interface BorrowService {

    void requestBorrow(String username, Long bookId);

    void requestReturn(Long borrowId);

    List<BorrowHistory> getUserBorrowedBooks(String username);

    void approveBorrow(Long id);

    void approveReturn(Long id);

    List<BorrowHistory> getPendingBorrowRequests();

    List<BorrowHistory> getPendingReturnRequests();
}