package org.serc.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8120915017209979358L;
    
    public ResourceNotFoundException() {
        super("bad request");
    }
    
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
