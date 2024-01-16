package net.ausiasmarch.noventaveinticuatro.exception;

public class CannotPerformOperationException extends RuntimeException {

    public CannotPerformOperationException(String message) {
        super("ERROR: Cannot perform operation:" + message);
    }
    
}
