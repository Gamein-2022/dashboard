package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.core.exception.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.UnauthorizedException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Time;
import gamein2022.backend.dashboard.infrastructure.repository.TimeRepository;
import gamein2022.backend.dashboard.infrastructure.service.auth.AuthService;
import gamein2022.backend.dashboard.web.dto.result.ErrorResultDTO;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice(assignableTypes = {ProfileController.class,TeamController.class})
public class InitController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final AuthService authService;

    public InitController(AuthService authService) {
        this.authService = authService;
    }


    @ModelAttribute(name = "authInfo")
    public AuthInfo getLoginInformation(HttpServletRequest request) throws InvalidTokenException {
//        Time time = timeRepository.findById(1L).get();
//        if (time.getIsGamePaused()){
//            throw new InvalidTokenException("بازی متوقف است .");
//        }
        String token = request.getHeader("Authorization");
        if (token == null || token.length() < 8) {
            throw new InvalidTokenException("Invalid token!");
        }

        return authService.extractAuthInfoFromToken(token.substring(7));
    }

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<ErrorResultDTO> exception(InvalidTokenException exception){
        logger.error("Error: " + exception.getMessage());

        ErrorResultDTO result = new ErrorResultDTO(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(result, result.getStatus());
    }
}
