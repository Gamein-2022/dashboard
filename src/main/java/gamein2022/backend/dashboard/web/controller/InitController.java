package gamein2022.backend.dashboard.web.controller;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import gamein2022.backend.dashboard.infrastructure.util.RestUtil;
import gamein2022.backend.dashboard.web.dto.result.VerifyTokenResultDto;
import gamein2022.backend.dashboard.core.exception.login.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.login.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@ControllerAdvice
public class InitController {
    @Value("${gamein.auth.base.url}")
    private String authBaseUrl;

    @ModelAttribute(name = "userId")
    public String getLoginInformation(Model model,
                                      HttpSession httpSession,
                                      HttpServletRequest request) throws TokenNotFoundException, InvalidTokenException {
        if (request.getRequestURI().contains("swagger-resources") || request.getRequestURI().contains("api-docs"))
            return null;
        String token = request.getHeader("G-BT-TOKEN");
        if (Strings.isNullOrEmpty(token)) {
            throw new TokenNotFoundException();
        }
        Map<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("requestId", UUID.randomUUID().toString());

        String response = RestUtil.sendRawRequest(authBaseUrl + "", param, HttpMethod.POST, MediaType.APPLICATION_JSON);
        VerifyTokenResultDto resultDto = new Gson().fromJson(response, VerifyTokenResultDto.class);

        if (resultDto.isSuccessful())
            return resultDto.getUserId();
        else
            throw new InvalidTokenException();

    }
}