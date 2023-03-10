package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.infrastructure.service.auth.AuthService;
import gamein2022.backend.dashboard.web.dto.request.RegisterAndLoginRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.BaseResultDTO;
import gamein2022.backend.dashboard.web.dto.result.ErrorResultDTO;
import gamein2022.backend.dashboard.web.dto.result.RegisterAndLoginResultDTO;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> login(@RequestBody RegisterAndLoginRequestDTO request) {
        logger.info(request.getEmail(), " --- ", request.getPhone(), " --- ", request
                .getPassword());
        try {
            RegisterAndLoginResultDTO result = authService.login(request.getEmail(), request.getPhone(), request.getPassword());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.error(e.toString());
            ErrorResultDTO error = new ErrorResultDTO(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(error, error.getStatus());
        } catch (BadRequestException e) {
            logger.error(e.toString());
            ErrorResultDTO error = new ErrorResultDTO(e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> register(@RequestBody RegisterAndLoginRequestDTO request) {
        try {
            RegisterAndLoginResultDTO result = authService.register(request.getPhone(), request.getEmail(), request.getPassword());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (BadRequestException e) {
            logger.error(e.toString());
            ErrorResultDTO error = new ErrorResultDTO(e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

    @GetMapping(value = "/info",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> info(HttpServletRequest request) throws InvalidTokenException {
        try {
            String token = request.getHeader("Authorization");
            AuthInfo result = authService.extractAuthInfoFromToken(token);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidTokenException e) {
            logger.error(e.toString());
            ErrorResultDTO error = new ErrorResultDTO(e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }
}