package com.project.LibraryManagementSystem.controller;

import com.project.LibraryManagementSystem.entity.Book;
import com.project.LibraryManagementSystem.entity.BorrowHistory;
import com.project.LibraryManagementSystem.entity.User;
import com.project.LibraryManagementSystem.service.BookService;
import com.project.LibraryManagementSystem.service.BorrowService;
import com.project.LibraryManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    private final BookService bookService;
    private final UserService userService;
    private final BorrowService borrowService;

    @Autowired
    public LibrarianController(BookService bookService, UserService userService, BorrowService borrowService) {
        this.bookService = bookService;
        this.userService = userService;
        this.borrowService = borrowService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("pendingUsers", userService.getPendingUsers());
        model.addAttribute("borrowRequests", borrowService.getPendingBorrowRequests());
        model.addAttribute("returnRequests", borrowService.getPendingReturnRequests());

        // get all users who are not yet approved
        List<User> pendingUsers = userService.searchUsersByEnabled(false);
        model.addAttribute("pendingUsers", pendingUsers);
        return "dashboard";
    }

    @GetMapping("/books/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/books/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/librarian/books";
    }

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/books/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/books/update")
    public String updateBook(@ModelAttribute Book book) {
        bookService.updateBook(book);
        return "redirect:/librarian/books";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/librarian/books";
    }

    @PostMapping("/borrow/approve/{id}")
    public String approveBorrow(@PathVariable Long id) {
        borrowService.approveBorrow(id);
        return "redirect:/librarian/borrow/requests";
    }


    @PostMapping("/return/approve/{id}")
    public String approveReturn(@PathVariable Long id) {

        borrowService.approveReturn(id);
        return "redirect:/librarian/borrow/requests";
    }


    @GetMapping("/users/requests")
    public String manageUserRequests(Model model) {

        List<User> pendingUsers = userService.getPendingUsers();
        model.addAttribute("pendingUsers", pendingUsers);

        return "manage-user-requests";
    }

    @GetMapping("/users/approve/{id}")
    public String approveUser(@PathVariable Long id) {

        userService.approveUser(id);
        return "redirect:/librarian/users/requests";
    }

    @GetMapping("/borrow/requests")
    public String manageBorrowRequests(Model model) {

        List<BorrowHistory> borrowRequests =
                borrowService.getPendingBorrowRequests();   // status = PENDING

        List<BorrowHistory> returnRequests =
                borrowService.getPendingReturnRequests();   // status = RETURN_REQUESTED

        model.addAttribute("borrowRequests", borrowRequests);
        model.addAttribute("returnRequests", returnRequests);

        return "manage-return-requests";
    }

}
