package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.core.exception.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.UnauthorizedException;
import gamein2022.backend.dashboard.infrastructure.service.auth.AuthService;
import gamein2022.backend.dashboard.web.dto.result.ErrorResultDTO;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@ControllerAdvice(assignableTypes = {ProfileController.class})
public class InitController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AuthService authService;

    public InitController(AuthService authService) {
        this.authService = authService;
    }


    @ModelAttribute(name = "authInfo")
    public AuthInfo getLoginInformation(Model model,
                                        HttpSession httpSession,
                                        HttpServletRequest request) throws InvalidTokenException,
            UnauthorizedException {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new UnauthorizedException("You don't have access to this resource");
        }

        return authService.extractAuthInfoFromToken(token);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<ErrorResultDTO> exception(UnauthorizedException exception){
        logger.error("Error: " + exception.getMessage());

        ErrorResultDTO result = new ErrorResultDTO(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(result, result.getStatus());
    }
    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<ErrorResultDTO> exception(InvalidTokenException exception){
        logger.error("Error: " + exception.getMessage());

        ErrorResultDTO result = new ErrorResultDTO(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(result, result.getStatus());
    }
}
