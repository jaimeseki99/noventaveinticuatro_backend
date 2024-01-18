package net.ausiasmarch.noventaveinticuatro.exception;

public class UnauthorizedException extends RuntimeException{
    
    public UnauthorizedException(String message) {
        super("Intento de acceso no autorizado: " + message);
    }
}
