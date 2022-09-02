package gamein2022.backend.dashboard.service.request.handler;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import gamein2022.backend.dashboard.exception.request.BaseRequestException;
import gamein2022.backend.dashboard.exception.request.InvalidRequestIdException;
import gamein2022.backend.dashboard.exception.request.RequestIdExistException;
import gamein2022.backend.dashboard.service.request.RequestCheckHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("requestHandler")
public class RequestHandler implements RequestCheckHandler {

    @Autowired
    @Qualifier("requestLogCache")
    Cache<String,Long> requestLog;

    @Override
    public void checkRequest(String requestId) throws BaseRequestException {
        //check if request id is null or empty
        if (Strings.isNullOrEmpty(requestId)) throw new InvalidRequestIdException();
        //check if request id is already sent in ten minute interval
        if (requestLog.getIfPresent(requestId) != null) throw new RequestIdExistException();

        //put request into the logCache
        requestLog.put(requestId,System.currentTimeMillis());
    }
}
