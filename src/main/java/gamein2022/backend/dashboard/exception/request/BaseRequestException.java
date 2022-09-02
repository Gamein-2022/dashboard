package gamein2022.backend.dashboard.exception.request;

import org.springframework.http.HttpStatus;

public class BaseRequestException extends Exception{
    HttpStatus status = HttpStatus.BAD_REQUEST;

    public HttpStatus getStatus() {
        return status;
    }
}
