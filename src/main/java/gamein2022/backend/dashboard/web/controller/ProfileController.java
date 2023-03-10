package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.infrastructure.service.profile.ProfileService;
import gamein2022.backend.dashboard.web.dto.request.ProfileInfoRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.ProfileInfoResultDTO;
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
    public ResponseEntity<ProfileInfoResultDTO> getInfo(@ModelAttribute("authInfo") AuthInfo authInfo) {
        try {
            ProfileInfoResultDTO result = profileService.getProfileInfo(authInfo.getUserId());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping(value = "/info",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileInfoResultDTO> setInfo(@ModelAttribute("authInfo") AuthInfo authInfo,
                                                        @RequestBody ProfileInfoRequestDTO request) {
        try {
            ProfileInfoResultDTO result = profileService.setProfileInfo(authInfo.getUserId(), request);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
