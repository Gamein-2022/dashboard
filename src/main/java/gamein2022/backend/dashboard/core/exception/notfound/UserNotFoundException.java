package gamein2022.backend.dashboard.core.exception.notfound;

public class UserNotFoundException extends NotFoundException{


    @Override
    public String getMessage() {
        return "User not found";
    }
}
