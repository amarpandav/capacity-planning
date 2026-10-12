package com.amarpandav.cpt.web.exception;

import com.amarpandav.cpt.infra.exception.NotPodLeadException;
import com.amarpandav.cpt.infra.exception.PodNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        log.error("An error appeared. ", ex);
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(PodNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(PodNotFoundException ex) {
        log.error("Pod not found.", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotPodLeadException.class)
    public ResponseEntity<NotPodLeadResponse> handleNotPodLeadException(NotPodLeadException ex) {
        log.warn("Rejected: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new NotPodLeadResponse(
                NotPodLeadResponse.CODE, ex.getMessage(), ex.getPodShortName(), ex.getPodName(), ex.getPodLeadNames()));
    }

    record ErrorResponse(String message) {}

    record NotPodLeadResponse(String code, String message, String podShortName, String podName, List<String> podLeadNames) {
        static final String CODE = "NOT_POD_LEAD";
    }
}
