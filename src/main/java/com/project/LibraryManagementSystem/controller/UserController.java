package com.project.LibraryManagementSystem.controller;

import com.project.LibraryManagementSystem.service.BookService;
import com.project.LibraryManagementSystem.service.BorrowService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final BookService bookService;
    private final BorrowService borrowService;

    public UserController(BookService bookService, BorrowService borrowService) {
        this.bookService = bookService;
        this.borrowService = borrowService;
    }

    /**
     * USER DASHBOARD
     * view = books | borrow | return
     */
    @GetMapping("/dashboard")
    public String userDashboard(
            @RequestParam(required = false) String view,
            Model model,
            Authentication authentication
    ) {

        // 1. Greeting username
        String username = authentication.getName();
        model.addAttribute("username", username);

        // 2. Control which table to show
        if ("books".equals(view)) {
            model.addAttribute("books", bookService.getAllBooks());
        }

        if ("borrow".equals(view)) {
            model.addAttribute("books", bookService.getAllBooks());
        }

        if ("return".equals(view)) {
            model.addAttribute(
                    "borrowedBooks",
                    borrowService.getUserBorrowedBooks(username)
            );
        }

        model.addAttribute("view", view);
        return "user-dashboard";
    }

    /**
     * BORROW BOOK REQUEST
     */
    @PostMapping("/borrow/{bookId}")
    public String requestBorrow(
            @PathVariable Long bookId,
            Authentication authentication
    ) {
        String username = authentication.getName();
        borrowService.requestBorrow(username, bookId);
        return "redirect:/user/dashboard?view=borrow";
    }

    /**
     * RETURN BOOK REQUEST
     */
    @PostMapping("/return/{borrowId}")
    public String requestReturn(@PathVariable Long borrowId) {
        borrowService.requestReturn(borrowId);
        return "redirect:/user/dashboard?view=return";
    }
}