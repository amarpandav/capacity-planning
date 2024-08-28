package com.ubs.cpt.infra.exception;

public class PodNotFoundException extends RuntimeException {
    public PodNotFoundException(String message) {
        super(message);
    }
}
