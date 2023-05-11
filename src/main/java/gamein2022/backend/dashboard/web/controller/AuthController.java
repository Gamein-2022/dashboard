package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.core.exception.*;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Time;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.TimeRepository;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
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
import java.util.Optional;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TimeRepository timeRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public AuthController(TimeRepository timeRepository, UserRepository userRepository, AuthService authService) {
        this.timeRepository = timeRepository;
        this.userRepository = userRepository;
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

    @PostMapping(value = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> register(@RequestBody RegisterAndLoginRequestDTO request) {
        try {
            RegisterAndLoginResultDTO result = authService.register(request.getPhone(), request.getEmail().toLowerCase(),
                    request.getPassword());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (BadRequestException | UserAlreadyExist e) {
            logger.error(e.toString());
            ErrorResultDTO error = new ErrorResultDTO(e.toString(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/info",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> info(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token == null || token.length() < 8) {
                throw new InvalidTokenException("Invalid token!");
            }
            AuthInfo result = authService.extractAuthInfoFromToken(token.substring(7));
            User user = userRepository.findById(result.getUserId()).get();
            Time time = timeRepository.findById(1L).get();
            if (time.getIsGamePaused() && ! user.isAdmin()){
                throw new PauseException("بازی متوقف است .");
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidTokenException e) {
            logger.error(e.toString());
            ErrorResultDTO error = new ErrorResultDTO(e.getMessage(), HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(error, error.getStatus());
        } catch (PauseException e) {
            logger.error(e.toString());
            ErrorResultDTO error = new ErrorResultDTO(e.getMessage(), HttpStatus.LOCKED);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }
    @GetMapping("time")
    public ResponseEntity<BaseResultDTO> getTime() {
        return new ResponseEntity<>(authService.getTime(), HttpStatus.OK);
    }
}