package gamein2022.backend.dashboard.core.exception;

public class UnauthorizedException extends Exception{
    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
