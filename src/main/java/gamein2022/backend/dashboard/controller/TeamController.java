package gamein2022.backend.dashboard.controller;

import gamein2022.backend.dashboard.data.dto.request.BaseRequest;
import gamein2022.backend.dashboard.data.dto.request.SetRegionRequest;
import gamein2022.backend.dashboard.data.dto.result.BaseResultDto;
import gamein2022.backend.dashboard.exception.notfound.NotFoundException;
import gamein2022.backend.dashboard.exception.notfound.TeamNotFoundException;
import gamein2022.backend.dashboard.exception.notfound.UserNotFoundException;
import gamein2022.backend.dashboard.exception.request.BaseRequestException;
import gamein2022.backend.dashboard.service.request.RequestCheckHandler;
import gamein2022.backend.dashboard.service.team.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("team")
public class TeamController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RequestCheckHandler requestHandler;
    private TeamService teamService;


    public TeamController(RequestCheckHandler requestHandler, TeamService teamService) {
        this.requestHandler = requestHandler;
        this.teamService = teamService;
    }

    @PostMapping("region-get")
    public ResponseEntity<Boolean> isRegionSet(@ModelAttribute("userId") String userId,BaseRequest request){
        try {
            requestHandler.checkRequest(request.getRequestId());
            Boolean hasTeamRegion = teamService.hasTeamRegion(userId);
            return new ResponseEntity<>(hasTeamRegion, HttpStatus.OK);

        } catch (BaseRequestException e) {
            logger.error("Error in team->region: " + e.getMessage());
            return new ResponseEntity<>(false,e.getStatus());
        } catch (NotFoundException e) {
            logger.error("Error in team->region: " + e.getMessage());
            return new ResponseEntity<>(false,e.getStatus());
        }

    }

    @PostMapping("region-set")
    public ResponseEntity<BaseResultDto> setTeamRegion(@ModelAttribute(name = "userId") String userId, @RequestBody SetRegionRequest request){
        try {
            requestHandler.checkRequest(request.getRequestId());
            BaseResultDto resultDto = teamService.setTeamRegion(request.getRegionId(),userId);
            return new ResponseEntity<>(resultDto,HttpStatus.OK);
        } catch (BaseRequestException e) {
            logger.error("Error in team->region: " + e.getMessage());
            return new ResponseEntity<>(new BaseResultDto(false,e.getMessage()),e.getStatus());
        } catch (NotFoundException e) {
            logger.error("Error in team->region: " + e.getMessage());
            return new ResponseEntity<>(new BaseResultDto(false,e.getMessage()),e.getStatus());
        }


    }


}
