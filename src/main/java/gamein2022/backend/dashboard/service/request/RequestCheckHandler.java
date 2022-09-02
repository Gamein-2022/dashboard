package gamein2022.backend.dashboard.service.request;

import gamein2022.backend.dashboard.exception.request.BaseRequestException;
import gamein2022.backend.dashboard.exception.request.InvalidRequestIdException;

public interface RequestCheckHandler {
    public void checkRequest(String requestId) throws  BaseRequestException;
}
