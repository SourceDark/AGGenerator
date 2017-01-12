package org.serc.exception;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -8120915017209979358L;
    
    public BadRequestException() {
        super("resource not found");
    }
    
    public BadRequestException(String message) {
        super(message);
    }

}
