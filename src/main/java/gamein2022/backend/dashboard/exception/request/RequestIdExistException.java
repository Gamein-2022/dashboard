package gamein2022.backend.dashboard.exception.request;

public class RequestIdExistException extends BaseRequestException{
    @Override
    public String getMessage() {
        return "RequestId is already exist";
    }
}
