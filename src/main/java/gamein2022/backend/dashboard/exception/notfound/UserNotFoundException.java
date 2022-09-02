package gamein2022.backend.dashboard.exception.notfound;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends NotFoundException{


    @Override
    public String getMessage() {
        return "User not found";
    }
}
