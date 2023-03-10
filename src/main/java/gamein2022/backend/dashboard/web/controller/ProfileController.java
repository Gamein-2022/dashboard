package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.infrastructure.service.profile.ProfileService;
import gamein2022.backend.dashboard.web.dto.request.ProfileInfoRequestDTO;
import gamein2022.backend.dashboard.web.dto.request.TeamInfoRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.BaseResultDTO;
import gamein2022.backend.dashboard.web.dto.result.ErrorResultDTO;
import gamein2022.backend.dashboard.web.dto.result.ProfileInfoResultDTO;
import gamein2022.backend.dashboard.web.dto.result.TeamInfoResultDTO;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = "/info",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> getInfo(@ModelAttribute("authInfo") AuthInfo authInfo) {
        try {
            ProfileInfoResultDTO result = profileService.getProfileInfo(authInfo.getUserId());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.error(e.toString());
            ErrorResultDTO error = new ErrorResultDTO(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }
    @PutMapping(value = "/info",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> setInfo(@ModelAttribute("authInfo") AuthInfo authInfo,
                                                        @RequestBody ProfileInfoRequestDTO request) {
        try {
            ProfileInfoResultDTO result = profileService.setProfileInfo(authInfo.getUserId(), request);
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

    @PostMapping(value = "/team",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> createTeam(@ModelAttribute("authInfo") AuthInfo authInfo,
                                                    @RequestBody TeamInfoRequestDTO request) {
        try {
            TeamInfoResultDTO result = profileService.createTeam(authInfo.getUserId(), request.getName());
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
}
