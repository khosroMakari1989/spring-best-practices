package com.kmv.geological.exception.handler;

import com.kmv.geological.exception.general.DuplicateResourceException;
import com.kmv.geological.exception.general.NoSuchResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * spring MessageSource could be used here to return message if needed.
 *
 * @author khosro.makari@gmail.com
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GeneralExceptionsHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralExceptionsHandler.class);

    @ExceptionHandler({DuplicateResourceException.class})
    protected ResponseEntity<Object> handleInvalidRequest(DuplicateResourceException exception, WebRequest request) {
        LOGGER.error("The resource already exists! {}", exception.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(exception, null, headers, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({NoSuchResourceException.class})
    protected ResponseEntity<Object> handleNoSuchResourceException(NoSuchResourceException exception, WebRequest request) {
        LOGGER.error("The resource doesn't exist! {}", exception.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(exception, null, headers, HttpStatus.NOT_FOUND, request);
    }
}
