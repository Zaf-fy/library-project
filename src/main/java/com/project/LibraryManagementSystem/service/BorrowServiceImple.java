package com.project.LibraryManagementSystem.service;

import com.project.LibraryManagementSystem.JpaRepository.BookRepository;
import com.project.LibraryManagementSystem.JpaRepository.BorrowRepository;
import com.project.LibraryManagementSystem.JpaRepository.UserRepository;
import com.project.LibraryManagementSystem.entity.Book;
import com.project.LibraryManagementSystem.entity.BorrowHistory;
import com.project.LibraryManagementSystem.entity.User;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowServiceImple implements BorrowService {

    private final BorrowRepository borrowRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;

    public BorrowServiceImple(BorrowRepository borrowRepo,
                         UserRepository userRepo,
                         BookRepository bookRepo) {
        this.borrowRepo = borrowRepo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    // USER REQUESTS BORROW (NOT BORROWING YET)
    public void requestBorrow(String username, Long bookId) {

        User user = userRepo.findByUsername(username).orElseThrow();
        Book book = bookRepo.findById(bookId).orElseThrow();

        BorrowHistory bh = new BorrowHistory();
        bh.setUser(user);
        bh.setBook(book);
        bh.setRequestedAt(LocalDateTime.now());
        bh.setStatus("PENDING"); // IMPORTANT

        borrowRepo.save(bh);
    }

    @Override
    // ADMIN APPROVES BORROW
    public void approveBorrow(Long borrowId) {
        BorrowHistory bh = borrowRepo.findById(borrowId).orElseThrow();
        bh.setStatus("APPROVED");
        bh.setApprovedAt(LocalDateTime.now());
        borrowRepo.save(bh);
    }

    @Override
    // USER REQUESTS RETURN
    public void requestReturn(Long borrowId) {
        BorrowHistory bh = borrowRepo.findById(borrowId).orElseThrow();
        bh.setStatus("RETURN_REQUESTED");
        borrowRepo.save(bh);
    }

    @Override
    // ADMIN APPROVES RETURN
    public void approveReturn(Long borrowId) {
        BorrowHistory bh = borrowRepo.findById(borrowId).orElseThrow();
        bh.setStatus("RETURNED");
        bh.setReturnedAt(LocalDateTime.now());
        borrowRepo.save(bh);
    }

    @Override
    public List<BorrowHistory> getUserBorrowedBooks(String username) {
        return borrowRepo.findByUserUsername(username);
    }

    @Override
    public List<BorrowHistory> getPendingBorrowRequests() {
        return borrowRepo.findByStatus("PENDING");
    }

    @Override
    public List<BorrowHistory> getPendingReturnRequests() {
        return borrowRepo.findByStatus("RETURN_REQUESTED");
    }



}
