package com.tntu.server.docs.communication.handlers;

import com.tntu.server.docs.communication.models.auth.InvalidTokenException;
import com.tntu.server.docs.communication.models.auth.TokenBlockedException;
import com.tntu.server.docs.communication.models.responses.Response;
import com.tntu.server.docs.communication.models.responses.ResponseFactory;
import com.tntu.server.docs.core.models.exceptions.*;
import com.tntu.server.docs.core.models.exceptions.file.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.AuthenticationFailedException;

@ControllerAdvice
public final class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {
            AccessDeniedException.class,
            AuthenticationFailedException.class,
            InvalidTokenException.class,
            LoginFailedException.class,
            TokenBlockedException.class,
    })
    protected ResponseEntity<?> handleForbidden(Exception exception, WebRequest request) {
        var response = ResponseFactory.createForbidden(exception);

        return handleException(exception, response, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {
            RoleNotFoundException.class,
            UserNotFoundException.class,
            RegistrationCodeNotFoundException.class,
            ResourceNotExistsException.class,
            FileNotExistsException.class,
    })
    protected ResponseEntity<?> handleNotFound(Exception exception, WebRequest request) {
        var response = ResponseFactory.createNotFound(exception);

        return handleException(exception, response, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {
            CanNotCreateUserException.class,
            CanNotSendMailException.class,
            UserAlreadyExistsException.class,
            UserIsBlockedException.class,
            ActionOnAdminRoleException.class,
            CanNotReadFileException.class,
            InvalidResourceException.class,
            CanNotWriteFileException.class,
            DeleteFileException.class,
            CanNotCreateDirectoryException.class,
            FileAlreadyExistsException.class,
            CanNotDeleteDirectoryException.class,
            CanNotMoveException.class,
            CanNotCreateDirectoryException.class,
    })
    protected ResponseEntity<?> handleConflict(Exception exception, WebRequest request) {
        var response = ResponseFactory.createConflict(exception);

        return handleException(exception, response, HttpStatus.CONFLICT, request);
    }

    private ResponseEntity<?> handleException(Exception exception, Response<?> response, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }
}
