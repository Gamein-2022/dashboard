package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UnauthorizedException;
import gamein2022.backend.dashboard.infrastructure.service.team_building.TeamBuildingService;
import gamein2022.backend.dashboard.web.dto.request.AcceptTeamOfferRequestDTO;
import gamein2022.backend.dashboard.web.dto.request.CreateTeamOfferRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.*;
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
    public ResponseEntity<List<UserDTO>> getUsers(@ModelAttribute AuthInfo authInfo) {
        return new ResponseEntity<>(serviceHandler.getUsers(authInfo.getUserId()), HttpStatus.OK);
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

    @GetMapping(value = "sent-offers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getTeamOffers(@ModelAttribute AuthInfo authInfo) {
        try {
            return new ResponseEntity<>(serviceHandler.getTeamOffers(authInfo.getTeamId(), authInfo.getUserId()), HttpStatus.OK);
        } catch (BadRequestException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "accept-offer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> acceptTeamJoin(@ModelAttribute AuthInfo authInfo,
                                                            @RequestBody AcceptTeamOfferRequestDTO request) {
        try {
            return new ResponseEntity<>(serviceHandler.acceptOffer(authInfo.getUserId(), request.getOfferId()), HttpStatus.OK);
        } catch (BadRequestException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> leaveTeam(@ModelAttribute AuthInfo authInfo) {
        try {
            return new ResponseEntity<>(serviceHandler.leaveTeam(authInfo.getUserId()), HttpStatus.OK);
        } catch (BadRequestException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}
