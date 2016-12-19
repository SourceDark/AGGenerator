package org.serc.exception;

public class ActionForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 95698501357163792L;

    public ActionForbiddenException() {
        super("action forbidden");
    }
    
    public ActionForbiddenException(String message) {
        super(message);
    }

}
