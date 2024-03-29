package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.enums.LogType;
import gamein2022.backend.dashboard.infrastructure.service.team.TeamServiceHandler;
import gamein2022.backend.dashboard.web.dto.request.SetTeamRegionRequestDTO;
import gamein2022.backend.dashboard.web.dto.request.TeamInfoRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.*;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("team")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TeamController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TeamServiceHandler teamServiceHandler;

    public TeamController(TeamServiceHandler teamServiceHandler) {
        this.teamServiceHandler = teamServiceHandler;
    }


    @GetMapping("region")
    public ResponseEntity<BaseResultDTO> getTeamRegion(
            @ModelAttribute("authInfo") AuthInfo authInfo
    ) {
        try {
            RegionResultDTO regionResultDTO = teamServiceHandler.getTeamRegion(authInfo.getTeamId());
            return new ResponseEntity<>(regionResultDTO, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO("Team Not Found", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResultDTO> getTeamInfo(@ModelAttribute("authInfo") AuthInfo authInfo) {
        try {
            return new ResponseEntity<>(teamServiceHandler.getTeamInfo(authInfo.getTeamId(), authInfo.getUserId()),
                    HttpStatus.OK);
        } catch (BadRequestException e) {
            logger.error(e.toString());
            ErrorResultDTO error = new ErrorResultDTO(e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

    @GetMapping("logs")
    public ResponseEntity<BaseResultDTO> getTeamLogs(
            @ModelAttribute("authInfo") AuthInfo authInfo,
            @RequestParam("t") LogType logType
    ) {
        try {
            GetTeamLogsResultDTO result = teamServiceHandler.getTeamLogs(authInfo,logType);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            logger.error(e.toString(),e);
            ErrorResultDTO error = new ErrorResultDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

//    @GetMapping("wealth")
//    public ResponseEntity<BaseResultDTO> getTeamWealth(
//            @ModelAttribute("authInfo") AuthInfo authInfo
//    ){
//        try {
//            GetTeamWealthResultDTO resultDTO = teamServiceHandler.getTeamWealth(authInfo.getTeamId());
//            return new ResponseEntity<>(resultDTO,HttpStatus.OK);
//        }catch (Exception e){
//            logger.error(e.toString(),e);
//            ErrorResultDTO error = new ErrorResultDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>(error, error.getStatus());
//        }
//    }

    @GetMapping("rank")
    public ResponseEntity<BaseResultDTO> getTeamRank (
        @ModelAttribute("authInfo") AuthInfo authInfo
    ){
        try {
            return new ResponseEntity<>(teamServiceHandler.getTeamRank(authInfo.getTeamId()),HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.toString(),e);
            ErrorResultDTO error = new ErrorResultDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

}
