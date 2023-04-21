package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.UnauthorizedException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
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

@ControllerAdvice(assignableTypes = {BackPanelController.class})
public class PanelController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AuthService authService;

    public PanelController(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute(name = "admin")
    public void getLoginInformation(HttpServletRequest request) throws InvalidTokenException, BadRequestException, UnauthorizedException {
        String token = request.getHeader("Authorization");
        if (token == null || token.length() < 8) {
            throw new InvalidTokenException("Invalid token!");
        }
        AuthInfo authInfo = authService.extractAuthInfoFromToken(token.substring(7));
        User user = authService.getUserById(authInfo.getUserId());

        if (! user.isAdmin()){
            throw new UnauthorizedException("شما دسترسی به این صفحه ندارید.");
        }
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResultDTO> exception(InvalidTokenException exception){
        logger.error("Error: " + exception.getMessage());
        ErrorResultDTO result = new ErrorResultDTO(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(result, result.getStatus());
    }
}
