package com.hslcreator.LibraryAPI.services;


import com.hslcreator.LibraryAPI.exceptions.BookNotFoundException;
import com.hslcreator.LibraryAPI.exceptions.BookRequestNotFoundException;
import com.hslcreator.LibraryAPI.exceptions.ChangeDateRequestNotFoundException;
import com.hslcreator.LibraryAPI.exceptions.UnauthorizedException;
import com.hslcreator.LibraryAPI.exceptions.UserNotFoundException;
import com.hslcreator.LibraryAPI.models.entities.ApprovalStatus;
import com.hslcreator.LibraryAPI.models.entities.Book;
import com.hslcreator.LibraryAPI.models.entities.BookDepartment;
import com.hslcreator.LibraryAPI.models.entities.BookRequest;
import com.hslcreator.LibraryAPI.models.entities.ChangeDateRequest;
import com.hslcreator.LibraryAPI.models.entities.Department;
import com.hslcreator.LibraryAPI.models.entities.Role;
import com.hslcreator.LibraryAPI.models.entities.User;
import com.hslcreator.LibraryAPI.models.requests.BookDto;
import com.hslcreator.LibraryAPI.models.requests.BookSearchRequest;
import com.hslcreator.LibraryAPI.models.requests.BorrowBookRequest;
import com.hslcreator.LibraryAPI.models.requests.ChangeDateDto;
import com.hslcreator.LibraryAPI.models.responses.BookRequestResponse;
import com.hslcreator.LibraryAPI.models.responses.BookResponse;
import com.hslcreator.LibraryAPI.models.responses.ChangeDueDateResponse;
import com.hslcreator.LibraryAPI.models.responses.GenericResponse;
import com.hslcreator.LibraryAPI.repositories.BookDepartmentRepository;
import com.hslcreator.LibraryAPI.repositories.BookRepository;
import com.hslcreator.LibraryAPI.repositories.BookRequestRepository;
import com.hslcreator.LibraryAPI.repositories.ChangeDateRequestRepository;
import com.hslcreator.LibraryAPI.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final BookDepartmentRepository bookDepartmentRepository;
    private final BookRequestRepository bookRequestRepository;
    private final UserService userService;
    private final ChangeDateRequestRepository changeDateRequestRepository;

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
                .status(ApprovalStatus.PENDING)
                .createdAt(Instant.now())
                .build();

        bookRequestRepository.save(bookRequest);

        return BookRequestResponse.builder()
                .message("Book has been requested")
                .bookRequestId(bookRequest.getBookRequestId())
                .approvalStatus(bookRequest.getStatus())
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

    public GenericResponse changeDueDate(int bookRequestId, ChangeDateDto changeDateDto) {

        BookRequest bookRequest = bookRequestRepository.findByBookRequestId(bookRequestId).orElseThrow(BookRequestNotFoundException::new);

        ChangeDateRequest changeDateRequest;

        if (changeDateRequestRepository.existsByBookRequestId(bookRequestId)) {

            changeDateRequest = changeDateRequestRepository.findByBookRequestId(bookRequestId).get();
            changeDateRequest.setNewDueDate(changeDateDto.getNewDueDate().toInstant());
        }

        else {

            changeDateRequest = ChangeDateRequest.builder()
                    .oldDueDate(bookRequest.getDueDate())
                    .newDueDate(changeDateDto.getNewDueDate().toInstant())
                    .bookRequestId(bookRequestId)
                    .userId(UserUtil.getLoggedInUser().get().getUserId())
                    .build();
        }

        changeDateRequestRepository.save(changeDateRequest);

        return GenericResponse.builder()
                .message("Request to change date has been sent").build();
    }

    public List<ChangeDueDateResponse> getAllChangeDueDateRequests() throws UnauthorizedException {

        throwErrorIfUserNotAdmin();

        return changeDateRequestRepository.findAll()
                .parallelStream().map(
                        changeDateRequest -> ChangeDueDateResponse.builder()
                                .oldDueDate(changeDateRequest.getOldDueDate())
                                .newDueDate(changeDateRequest.getNewDueDate())
                                .matricNumber(userService.findUserById(changeDateRequest.getUserId()).getMatricNumber())
                                .bookRequestId(changeDateRequest.getBookRequestId())
                                .build()
                ).toList();
    }

    public GenericResponse approveDueDate(int bookRequestId, ApprovalStatus approvalStatus) throws UnauthorizedException {

        throwErrorIfUserNotAdmin();

        ChangeDateRequest changeDateRequest = changeDateRequestRepository.findByBookRequestId(bookRequestId).orElseThrow(ChangeDateRequestNotFoundException::new);

        if (approvalStatus.equals(ApprovalStatus.APPROVED)) {

            BookRequest bookRequest = bookRequestRepository.findByBookRequestId(bookRequestId).orElseThrow(BookRequestNotFoundException::new);

            bookRequest.setDueDate(changeDateRequest.getNewDueDate());
            bookRequestRepository.save(bookRequest);

            changeDateRequestRepository.delete(changeDateRequest);

            return GenericResponse.builder()
                    .message("Request to change due date has been approved").build();

        }

        else {

            changeDateRequestRepository.delete(changeDateRequest);

            return GenericResponse.builder()
                    .message("Request to change due date has been declined").build();
        }
    }

    @Transactional
    public GenericResponse deleteBookRequest(int bookRequestId) {

        bookRequestRepository.deleteByBookRequestId(bookRequestRepository.findByBookRequestId(bookRequestId).orElseThrow(BookRequestNotFoundException::new).getBookRequestId());

        return GenericResponse.builder()
                .message("Book Request has been successfully deleted").build();
    }

    public List<BookResponse> searchForBookWithThisPattern(BookSearchRequest request) {

        if (request.getSearchString() == null) {

            request.setSearchString("");
        }

        User user = UserUtil.getLoggedInUser().get();

        Specification<Book> bookNameSpecification = (root, query, cb) -> cb.like(
                root.get("name"), ("%" + request.getSearchString().toUpperCase() + "%")
        );

        Specification<Book> authorNameSpecification = (root, query, cb) -> cb.like(
                root.get("author"), ("%" + request.getSearchString().toUpperCase() + "%")
        );

        List<BookResponse> response = new java.util.ArrayList<>(bookRepository.findAll(bookNameSpecification.or(authorNameSpecification)).parallelStream().map(book -> {

            BookDepartment bookDepartment = bookDepartmentRepository.findByBookId(book.getBookId()).get();
            if (!user.getDepartment().equals(bookDepartment.getDepartmentName())) {

                return null;
            }

            return book.toDto();
        }).toList());

        response.removeIf(Objects::isNull);

        return response;
    }

    public List<BookRequest> getAllBookRequests() {

        return bookRequestRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public GenericResponse approveRequestToBorrowBook(int bookRequestId, ApprovalStatus approvalStatus) {

        BookRequest bookRequest = bookRequestRepository.findByBookRequestId(bookRequestId).orElseThrow(BookRequestNotFoundException::new);

        bookRequest.setStatus(approvalStatus);

        bookRequestRepository.save(bookRequest);

        return GenericResponse.builder()
                .message("Request to borrow book has been approved")
                .build();
    }
}
