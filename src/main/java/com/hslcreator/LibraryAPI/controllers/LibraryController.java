package com.hslcreator.LibraryAPI.controllers;


import com.hslcreator.LibraryAPI.exceptions.BookNotFoundException;
import com.hslcreator.LibraryAPI.exceptions.UnauthorizedException;
import com.hslcreator.LibraryAPI.models.entities.BookRequest;
import com.hslcreator.LibraryAPI.models.entities.Department;
import com.hslcreator.LibraryAPI.models.requests.ApprovalStatusRequest;
import com.hslcreator.LibraryAPI.models.requests.BookDto;
import com.hslcreator.LibraryAPI.models.requests.BorrowBookRequest;
import com.hslcreator.LibraryAPI.models.responses.BookResponse;
import com.hslcreator.LibraryAPI.models.responses.GenericResponse;
import com.hslcreator.LibraryAPI.services.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("library/")
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping("books")
    public GenericResponse uploadBook(@RequestBody BookDto bookDto) throws UnauthorizedException {

        return libraryService.uploadBook(bookDto);
    }

    @GetMapping("books/{id}")
    public BookResponse getBookById(@PathVariable int id) throws BookNotFoundException {

        return libraryService.getBookById(id);
    }

    @GetMapping("books/all/{dept}")
    public Set<BookResponse> findAllBooksForDepartment(@PathVariable Department dept) throws BookNotFoundException {

        return libraryService.findAllBooksForDepartment(dept);
    }

    @DeleteMapping("books/{id}")
    public boolean deleteBook(@PathVariable int id) throws UnauthorizedException {

        return libraryService.deleteBook(id);
    }

    @PostMapping("books/request/{bookId}")
    public GenericResponse requestBook(@PathVariable int bookId, @RequestBody BorrowBookRequest request) {

        return libraryService.requestToBorrowBook(bookId, request);
    }

    @GetMapping("books/requests")
    public List<BookRequest> getAllBookRequests() throws UnauthorizedException {

        return libraryService.getAllBookRequests();
    }

    @GetMapping("books/requests/{bookRequestId}")
    public BookRequest getBookRequestById(@PathVariable int bookRequestId) throws UnauthorizedException {

        return libraryService.getBookRequestById(bookRequestId);
    }

    @PutMapping("books/requests/{bookRequestId}")
    public GenericResponse approveBookRequest(@PathVariable int bookRequestId, @RequestBody ApprovalStatusRequest approvalStatusRequest) throws UnauthorizedException {

        return libraryService.approveBookRequest(bookRequestId, approvalStatusRequest.getApprovalStatus());
    }
}
