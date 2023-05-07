package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UnauthorizedException;
import gamein2022.backend.dashboard.infrastructure.service.team_building.TeamBuildingService;
import gamein2022.backend.dashboard.web.dto.request.CreateTeamOfferRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.BaseResultDTO;
import gamein2022.backend.dashboard.web.dto.result.ErrorResultDTO;
import gamein2022.backend.dashboard.web.dto.result.TeamOfferDTO;
import gamein2022.backend.dashboard.web.dto.result.UserDTO;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("team-building")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TeamBuildingController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TeamBuildingService serviceHandler;

    public TeamBuildingController(TeamBuildingService serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    @GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(serviceHandler.getUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "team-offer", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> createTeamOffer(@ModelAttribute AuthInfo authInfo,
                                                         @RequestBody CreateTeamOfferRequestDTO request) {
        try {
            return new ResponseEntity<>(serviceHandler.requestTeamJoin(authInfo.getTeamId(), authInfo.getUserId(),
                    request.getUserId()), HttpStatus.OK);
        } catch (UnauthorizedException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        } catch (BadRequestException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "offers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TeamOfferDTO>> getMyOffers(@ModelAttribute AuthInfo authInfo) {
        return new ResponseEntity<>(serviceHandler.getMyOffers(authInfo.getUserId()), HttpStatus.OK);
    }
}
