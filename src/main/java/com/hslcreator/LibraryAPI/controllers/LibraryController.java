package com.hslcreator.LibraryAPI.controllers;


import com.hslcreator.LibraryAPI.exceptions.BookNotFoundException;
import com.hslcreator.LibraryAPI.exceptions.UnauthorizedException;
import com.hslcreator.LibraryAPI.models.entities.BookRequest;
import com.hslcreator.LibraryAPI.models.entities.Department;
import com.hslcreator.LibraryAPI.models.requests.ApprovalStatusRequest;
import com.hslcreator.LibraryAPI.models.requests.BookDto;
import com.hslcreator.LibraryAPI.models.requests.BookSearchRequest;
import com.hslcreator.LibraryAPI.models.requests.BorrowBookRequest;
import com.hslcreator.LibraryAPI.models.requests.ChangeDateDto;
import com.hslcreator.LibraryAPI.models.responses.BookRequestResponse;
import com.hslcreator.LibraryAPI.models.responses.BookResponse;
import com.hslcreator.LibraryAPI.models.responses.ChangeDueDateResponse;
import com.hslcreator.LibraryAPI.models.responses.GenericResponse;
import com.hslcreator.LibraryAPI.services.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Upload book", description = "(Admin)Returns a validated jwt token that can be used to log in")
    @PostMapping("books")
    public BookResponse uploadBook(@RequestBody BookDto bookDto) throws UnauthorizedException {

        return libraryService.uploadBook(bookDto);
    }

    @Operation(summary = "Get Book by id", description = "Get Book by book id")
    @GetMapping("books/{id}")
    public BookResponse getBookById(@PathVariable int id) throws BookNotFoundException {

        return libraryService.getBookById(id);
    }

    @Operation(summary = "Find All Books for Department", description = "Find all books for specific department")
    @GetMapping("books/all/{dept}")
    public Set<BookResponse> findAllBooksForDepartment(@PathVariable Department dept) throws BookNotFoundException {

        return libraryService.findAllBooksForDepartment(dept);
    }

    @Operation(summary = "Delete Book", description = "(Admin)Delete Book by book id")
    @DeleteMapping("books/{id}")
    public boolean deleteBook(@PathVariable int id) throws UnauthorizedException {

        return libraryService.deleteBook(id);
    }

    @Operation(summary = "Request to borrow/reserve book", description = "Endpoint for requesting to borrow book")
    @PostMapping("books/request/{bookId}")
    public BookRequestResponse requestBook(@PathVariable int bookId, @RequestBody BorrowBookRequest request) {

        return libraryService.requestToBorrowBook(bookId, request);
    }

    @Operation(summary = "Approve/Reject Borrow Book Request")
    @PutMapping("books/requests/{bookRequestId}/approve")
    public GenericResponse approveRequestToBorrowBook(@PathVariable int bookRequestId, @RequestBody ApprovalStatusRequest approvalStatusRequest) {

        return libraryService.approveRequestToBorrowBook(bookRequestId, approvalStatusRequest.getApprovalStatus());
    }

    @Operation(summary = "Get All Book Requests", description = "Returns the date of request of books from latest to oldest")
    @GetMapping("books/requests/all")
    public List<BookRequest> getAllBookRequests() {

        return libraryService.getAllBookRequests();
    }

    @Operation(summary = "Change Due Date Request", description = "Endpoint for requesting to change due date (admin has to approve after)")
    @PutMapping("books/requests/change-due-date/{bookRequestId}")
    public GenericResponse changeDueDate(@PathVariable int bookRequestId, @RequestBody ChangeDateDto changeDateDto) {

        return libraryService.changeDueDate(bookRequestId, changeDateDto);
    }

    @Operation(summary = "Get All Change Due Date Requests for all users", description = "(Admin)Returns a list of all requests for book whether reserve or borrow(Admin)")
    @GetMapping("books/requests/change-due-date")
    public List<ChangeDueDateResponse> getAllChangeDueDateRequests() throws UnauthorizedException {

        return libraryService.getAllChangeDueDateRequests();
    }

    @Operation(summary = "Approve Due Date User", description = "(Admin) can only approve due date")
    @PutMapping("books/requests/{bookRequestId}/approve-due-date")
    public GenericResponse approveDueDate(@PathVariable int bookRequestId, @RequestBody ApprovalStatusRequest approvalStatusRequest) throws UnauthorizedException {

        return libraryService.approveDueDate(bookRequestId, approvalStatusRequest.getApprovalStatus());
    }

    @Operation(summary = "Get Book Request by Book Request Id", description = "(Admin)Get book request by book request id")
    @GetMapping("books/requests/{bookRequestId}")
    public BookRequest getBookRequestById(@PathVariable int bookRequestId) throws UnauthorizedException {

        return libraryService.getBookRequestById(bookRequestId);
    }

    @Operation(summary = "Delete Book Request", description = "Mostly used by users to delete book request")
    @DeleteMapping("books/requests/{bookRequestId}")
    public GenericResponse deleteBookRequest(@PathVariable int bookRequestId) {

        return libraryService.deleteBookRequest(bookRequestId);
    }

    @Operation(summary = "Searching for books in library", description = "For the search string you can either pass in author name or book name. It uses like in the where clause to search for books. Also disregard the fact that its a post endpoint, it is for getting all books with the parameters provided. Just take a look at the response type.")
    @PostMapping("books/search")
    public List<BookResponse> searchForBooksWithThisPattern(@RequestBody BookSearchRequest request) {

        return libraryService.searchForBookWithThisPattern(request);
    }
}
