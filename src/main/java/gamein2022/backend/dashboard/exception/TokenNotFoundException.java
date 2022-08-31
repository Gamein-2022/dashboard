package gamein2022.backend.dashboard.exception;

import org.springframework.http.HttpStatus;

public class TokenNotFoundException extends Exception{
    HttpStatus status = HttpStatus.BAD_REQUEST;

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return "Token not found";
    }
}
