package com.hslcreator.LibraryAPI.controllerutils;

import com.hslcreator.LibraryAPI.exceptions.EntityNotFoundException;
import com.hslcreator.LibraryAPI.exceptions.UnauthorizedException;
import com.hslcreator.LibraryAPI.utils.AppResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountLockedException;
import java.time.Instant;
import java.time.temporal.ChronoField;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AppResponse handleValidationExceptions(UnauthorizedException ex) {
        log.error("Unauthorised entity accessing resource", ex);
        return AppResponseUtil.buildErrorResponse("Unauthorized to access resource due to "+ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppResponse handleBadCredentialsException(EntityNotFoundException ex) {
        log.error("Entity not found", ex);
        return AppResponseUtil.buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        Long timestamp = Instant.now().getLong(ChronoField.INSTANT_SECONDS);
        log.error(timestamp + " " + ex.getMessage(), ex);
        return AppResponseUtil.buildErrorResponse("Kindly Contact the administrator Reference: " + timestamp);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public AppResponse handleGenericException(Exception ex) {
        log.error("An error occurred: ", ex);
        return AppResponseUtil.buildErrorResponse("An error occurred while handling your request");
    }

    @ExceptionHandler(AccountLockedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AppResponse handleAccountLockedException(AccountLockedException ex){
        log.error(ex.getMessage(), ex);
        return AppResponseUtil.buildErrorResponse("Account has been locked");
    }
}
