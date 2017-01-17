package org.serc;

import javax.servlet.http.HttpServletRequest;

import org.serc.exception.ActionForbiddenException;
import org.serc.exception.BadRequestException;
import org.serc.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.google.common.base.Throwables;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger("global.logger");

    static class ErrorInfo {

        public final int code;

        public final String url;

        public final String ex;

        public ErrorInfo(int code, String url, Exception ex) {
            this.code = code;
            this.url = url;
            this.ex = Throwables.getStackTraceAsString(ex);
        }
    }
    

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorInfo> handleInvalidParamsException(HttpServletRequest request, Exception ex) {
        ErrorInfo e = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), ex);
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ActionForbiddenException.class)
    public ResponseEntity<ErrorInfo> handleAccessDeniedException(HttpServletRequest request, Exception ex) {
        ErrorInfo e = new ErrorInfo(HttpStatus.FORBIDDEN.value(), request.getRequestURI(), ex);
        return new ResponseEntity<>(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleResourceNotFoundException(HttpServletRequest request, Exception ex) {
        ErrorInfo e = new ErrorInfo(HttpStatus.NOT_FOUND.value(), request.getRequestURI(), ex);
        logger.info("resource not found: {}", ex.getMessage());
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorInfo> handleBadRequestException(HttpServletRequest request, Exception ex) {
        ErrorInfo e = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), ex);
        logger.info("resource not found: {}", ex.getMessage());
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }


}
