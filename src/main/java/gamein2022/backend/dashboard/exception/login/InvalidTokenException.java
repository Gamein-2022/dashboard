package gamein2022.backend.dashboard.exception.login;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends Exception{
    HttpStatus status = HttpStatus.BAD_REQUEST;

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return "Invalid Token";
    }
}
