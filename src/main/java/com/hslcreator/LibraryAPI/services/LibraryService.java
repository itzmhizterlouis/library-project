package com.hslcreator.LibraryAPI.services;


import com.hslcreator.LibraryAPI.exceptions.BookNotFoundException;
import com.hslcreator.LibraryAPI.exceptions.BookRequestNotFoundException;
import com.hslcreator.LibraryAPI.exceptions.UnauthorizedException;
import com.hslcreator.LibraryAPI.exceptions.UserNotFoundException;
import com.hslcreator.LibraryAPI.models.entities.ApprovalStatus;
import com.hslcreator.LibraryAPI.models.entities.Book;
import com.hslcreator.LibraryAPI.models.entities.BookDepartment;
import com.hslcreator.LibraryAPI.models.entities.BookRequest;
import com.hslcreator.LibraryAPI.models.entities.Department;
import com.hslcreator.LibraryAPI.models.entities.Role;
import com.hslcreator.LibraryAPI.models.requests.BookDto;
import com.hslcreator.LibraryAPI.models.requests.BookSearchRequest;
import com.hslcreator.LibraryAPI.models.requests.BorrowBookRequest;
import com.hslcreator.LibraryAPI.models.requests.ChangeDateRequest;
import com.hslcreator.LibraryAPI.models.responses.BookRequestResponse;
import com.hslcreator.LibraryAPI.models.responses.BookResponse;
import com.hslcreator.LibraryAPI.models.responses.GenericResponse;
import com.hslcreator.LibraryAPI.repositories.BookDepartmentRepository;
import com.hslcreator.LibraryAPI.repositories.BookRepository;
import com.hslcreator.LibraryAPI.repositories.BookRequestRepository;
import com.hslcreator.LibraryAPI.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final BookDepartmentRepository bookDepartmentRepository;
    private final BookRequestRepository bookRequestRepository;

    public BookResponse uploadBook(BookDto bookDto) throws UnauthorizedException {

        throwErrorIfUserNotAdmin();

        Book book = Book.builder()
                .name(bookDto.getName().toUpperCase())
                .about(bookDto.getAbout().toLowerCase())
                .author(bookDto.getAuthor().toUpperCase())
                .build();

        bookRepository.save(book);

        bookDto.getDepartments().forEach(department ->
                bookDepartmentRepository.save(BookDepartment.builder()
                        .bookId(book.getBookId())
                        .departmentName(department)
                        .build()
                ));

        return book.toDto();
    }

    public BookResponse getBookById(int id) throws BookNotFoundException {

        return bookRepository.findById((long)id).orElseThrow(BookNotFoundException::new).toDto();
    }

    public Set<BookResponse> findAllBooksForDepartment(Department department) throws BookNotFoundException {

        Set<BookResponse> response = new HashSet<>();

        Set<Integer> bookIds = bookDepartmentRepository.findAllByDepartmentName(department).stream().map(BookDepartment::getBookId).collect(Collectors.toSet());

        for (int bookId: bookIds) {

            response.add(bookRepository.findByBookId(bookId).orElseThrow(BookNotFoundException::new).toDto());
        }

        return response;
    }

    @Transactional
    public boolean deleteBook(int id) throws UnauthorizedException {

        throwErrorIfUserNotAdmin();

        bookDepartmentRepository.deleteByBookId(id);
        bookRepository.deleteById((long)id);

        return true;
    }

    public BookRequestResponse requestToBorrowBook(int bookId, BorrowBookRequest request) {

        BookRequest bookRequest = BookRequest.builder()
                .userId(UserUtil.getLoggedInUser().orElseThrow(UserNotFoundException::new).getUserId())
                .bookRequestType(request.getBookRequestType())
                .description(request.getDescription())
                .pickUpDate(request.getPickUpDate().toInstant())
                .dueDate(request.getDueDate().toInstant())
                .bookId(bookId)
                .status(ApprovalStatus.NULL)
                .build();

        bookRequestRepository.save(bookRequest);

        return BookRequestResponse.builder()
                .message("Book has been requested")
                .bookRequestId(bookRequest.getBookRequestId())
                .build();
    }

    public BookRequest getBookRequestById(int id) throws UnauthorizedException {

        throwErrorIfUserNotAdmin();

        return bookRequestRepository.findByBookRequestId(id).orElseThrow(BookRequestNotFoundException::new);
    }

    public static void throwErrorIfUserNotAdmin() throws UnauthorizedException {

        if (!UserUtil.getLoggedInUser().get().getRole().equals(Role.ADMIN)) {

            throw new UnauthorizedException("You're not an admin");
        }
    }

    public GenericResponse changeDueDate(int bookRequestId, ChangeDateRequest changeDateRequest) {

        BookRequest bookRequest = bookRequestRepository.findByBookRequestId(bookRequestId).orElseThrow(BookRequestNotFoundException::new);
        bookRequest.setDueDate(changeDateRequest.getNewDueDate().toInstant());
        bookRequest.setStatus(ApprovalStatus.NULL);
        bookRequestRepository.save(bookRequest);

        return GenericResponse.builder()
                .message("Date has been successfully changed").build();
    }

    public List<BookRequest> getAllBookRequests() throws UnauthorizedException {

        throwErrorIfUserNotAdmin();

        return bookRequestRepository.findAllByStatus(ApprovalStatus.NULL);
    }

    public GenericResponse approveDueDate(int bookRequestId, ApprovalStatus approvalStatus) throws UnauthorizedException {

        throwErrorIfUserNotAdmin();

        BookRequest bookRequest = bookRequestRepository.findByBookRequestId(bookRequestId).orElseThrow(BookRequestNotFoundException::new);
        bookRequest.setStatus(approvalStatus);
        bookRequestRepository.save(bookRequest);

        return GenericResponse.builder()
                .message("Due date has been successfully approved").build();
    }

    @Transactional
    public GenericResponse deleteBookRequest(int bookRequestId) {

        bookRequestRepository.deleteByBookRequestId(bookRequestRepository.findByBookRequestId(bookRequestId).orElseThrow(BookRequestNotFoundException::new).getBookRequestId());

        return GenericResponse.builder()
                .message("Book Request has been successfully deleted").build();
    }

    public List<BookResponse> searchForBookWithThisPattern(BookSearchRequest request) {

        if (request.getBookName() == null || request.getAuthor() == null) {

            request.setAuthor("");
            request.setBookName("");
        }

        Specification<Book> bookNameSpecification = (root, query, cb) -> cb.like(
                root.get("name"), ("%" + request.getBookName().toUpperCase() + "%")
        );

        Specification<Book> authorNameSpecification = (root, query, cb) -> cb.like(
                root.get("author"), ("%" + request.getAuthor().toUpperCase() + "%")
        );

        return bookRepository.findAll(bookNameSpecification.or(authorNameSpecification)).parallelStream().map(Book::toDto).toList();
    }
}
