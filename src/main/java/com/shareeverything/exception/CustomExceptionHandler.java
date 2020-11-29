package com.shareeverything.exception;

import com.shareeverything.constant.ResponseStatus;
import com.shareeverything.dto.BaseResponseDto;
import com.shareeverything.dto.response.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    // 400

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error("Exception: {}", ex.getClass().getName());
        log.error("Stack trace ", ex);

        List<String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        if(!validationErrors.isEmpty()){
            ValidationErrorResponse validationErrorResponse = ValidationErrorResponse.builder()
                    .validationError(validationErrors)
                    .build();
            return handleExceptionInternal(ex, validationErrorResponse, headers, HttpStatus.BAD_REQUEST, request);
        }
        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                .message("This operation was not successful.")
                .build();
        return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error("Exception: {}", ex.getClass().getName());
        log.error("Stack trace ", ex);

        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                //.message(ex.getLocalizedMessage())
                .message("There was an error. Please, try again later.")
                .build();

        return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error("Exception: {}", ex.getClass().getName());
        log.error("Stack trace ", ex);

        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                //.message(ex.getLocalizedMessage())
                .message("There was an error. Please, try again later.")
                .build();
        return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error("Exception: {}", ex.getClass().getName());
        log.error("Stack trace ", ex);

        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                //.message(ex.getLocalizedMessage())
                .message("There was an error. Please, try again later.")
                .build();
        return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error("Exception: {}", ex.getClass().getName());
        log.error("Stack trace ", ex);

        final String error = ex.getParameterName() + " parameter is missing";

        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                .message(error)
                .build();

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        log.error("Exception: {}", ex.getClass().getName());
        log.error("Stack trace ", ex);

        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                .message(error)
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        log.error("Exception: {}", ex.getClass().getName());
        log.error("Stack trace ", ex);

        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                .message("There was an error. Please, try again later.")
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    // 404
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error("Exception: {}", ex.getClass().getName());
        log.error("Stack trace ", ex);

        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                //.message(error)
                .message("Resource not found!")
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // 405
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error("Exception: {}", ex.getClass().getName());
        log.error("Stack trace ", ex);

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                .message(builder.toString())
                .build();

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error("Exception: {}", ex.getClass().getName());
        log.error("Stack trace ", ex);

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                .message(builder.toString())
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        log.error("Exception: {}", ex.getClass().getName());
        log.error("Stack trace ", ex);

        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                //.message(ex.getLocalizedMessage())
                .message("This operation was not successful.")
                .build();

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ShareEverythingException.class})
    public ResponseEntity<Object> handleTributeeException(final ShareEverythingException ex, final WebRequest request) {
        log.error("Tributee Exception {}", ex.getClass().getName());
        log.error("Stacktrace: ", ex);

        String message = ex.getMessage();
        if (message == null) {
            message = "Error occurred";
        }

        BaseResponseDto response = BaseResponseDto.builder()
                .status(ResponseStatus.FAILED)
                .message(message)
                .build();

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}