package gamein2022.backend.dashboard.core.exception;

public class UserAlreadyExist extends Exception{
    public UserAlreadyExist() {
    }

    public UserAlreadyExist(String message) {
        super(message);
    }
}
