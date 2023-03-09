package gamein2022.backend.dashboard.core.exception.notfound;

import org.springframework.http.HttpStatus;

public class NotFoundException extends Exception{
    HttpStatus status = HttpStatus.NOT_FOUND;

    public HttpStatus getStatus() {
        return status;
    }
}
