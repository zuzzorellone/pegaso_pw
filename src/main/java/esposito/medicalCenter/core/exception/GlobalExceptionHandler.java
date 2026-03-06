package esposito.medicalCenter.core.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        log.error("Resource not found: {}", ex.getMessage());

        ResponseErrorDTO error = new ResponseErrorDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ResponseErrorDTO> handleExpiredTokenException(ExpiredTokenException ex, HttpServletRequest request) {
        log.error("Expired token: {}", ex.getMessage());

        ResponseErrorDTO error = new ResponseErrorDTO(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Expired Token",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(FailedAttemptsException.class)
    public ResponseEntity<ResponseErrorDTO> handleFailedAttemptsException(FailedAttemptsException ex, HttpServletRequest request) { // Rinominato
        log.error("Failed more than 3 attempts: {}", ex.getMessage());

        ResponseErrorDTO error = new ResponseErrorDTO(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Too Many Failed Attempts",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(GenericReportDownloadException.class)
    public ResponseEntity<ResponseErrorDTO> handleGenericReportDownloadException(GenericReportDownloadException ex, HttpServletRequest request) { // Rinominato
        log.error("Failed to download report: {}", ex.getMessage());

        ResponseErrorDTO error = new ResponseErrorDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Download Failed",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReportFileNotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> handleFailedAttemptsException(ReportFileNotFoundException ex, HttpServletRequest request) { // Rinominato
        log.error("Reports File Not Found: {}", ex.getMessage());

        ResponseErrorDTO error = new ResponseErrorDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Reports File Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorDTO> handleGlobalException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error occurred: ", ex);

        ResponseErrorDTO error = new ResponseErrorDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred on our side",
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
