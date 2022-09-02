package gamein2022.backend.dashboard.exception.request;

public class InvalidRequestIdException extends BaseRequestException{
    @Override
    public String getMessage() {
        return "RequestId is invalid";
    }
}
