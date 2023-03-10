package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.infrastructure.service.user.UserService;
import gamein2022.backend.dashboard.web.dto.request.RegisterAndLoginRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.RegisterAndLoginResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterAndLoginResultDTO> login(@RequestBody RegisterAndLoginRequestDTO request) {
        logger.info(request.getEmail(), " --- ", request.getPhone(), " --- ", request
                .getPassword());
        try {
            RegisterAndLoginResultDTO result = userService.login(request.getEmail(), request.getPhone(), request.getPassword());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterAndLoginResultDTO> register(@RequestBody RegisterAndLoginRequestDTO request) {
        try {
            RegisterAndLoginResultDTO result = userService.register(request.getPhone(), request.getEmail(), request.getPassword());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}