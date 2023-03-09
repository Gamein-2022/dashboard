package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.web.dto.request.BaseRequest;
import gamein2022.backend.dashboard.web.dto.request.SetRegionRequest;
import gamein2022.backend.dashboard.web.dto.result.BaseResultDto;
import gamein2022.backend.dashboard.core.exception.notfound.NotFoundException;
import gamein2022.backend.dashboard.infrastructure.service.team.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("team")
public class TeamController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("getRegion")
    public ResponseEntity<Boolean> isRegionSet(@ModelAttribute("userId") String userId, BaseRequest request) {
        try {
            Boolean hasTeamRegion = teamService.hasTeamRegion(userId);
            return new ResponseEntity<>(hasTeamRegion, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Error in team->region: " + e.getMessage());
            return new ResponseEntity<>(false, e.getStatus());
        }
    }

    @PostMapping("setRegion")
    public ResponseEntity<BaseResultDto> setTeamRegion(@ModelAttribute(name = "userId") String userId, @RequestBody SetRegionRequest request) {
        try {
            BaseResultDto resultDto = teamService.setTeamRegion(request.getRegion(), userId);
            return new ResponseEntity<>(resultDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Error in team -> region: " + e.getMessage());
            return new ResponseEntity<>(new BaseResultDto(false, e.getMessage()), e.getStatus());
        }
    }
}