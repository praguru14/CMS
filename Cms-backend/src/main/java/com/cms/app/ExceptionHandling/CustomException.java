package com.cms.app.ExceptionHandling;

public class CustomException {

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public static class CustomValidationException extends RuntimeException {
        public CustomValidationException(String message) {
            super(message);
        }
    }
}
