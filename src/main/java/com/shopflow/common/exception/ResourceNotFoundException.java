package com.shopflow.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a requested resource (e.g., Product) cannot be found.
 *
 * <p>Using a specific exception type allows the {@link GlobalExceptionHandler}
 * to translate it to a proper HTTP 404 response, following senior‑engineer API design.
 * </p>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
