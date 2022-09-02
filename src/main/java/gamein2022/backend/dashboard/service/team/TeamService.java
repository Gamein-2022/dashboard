package gamein2022.backend.dashboard.service.team;

import gamein2022.backend.dashboard.data.dto.result.BaseResultDto;
import gamein2022.backend.dashboard.exception.notfound.TeamNotFoundException;
import gamein2022.backend.dashboard.exception.notfound.UserNotFoundException;

public interface TeamService {
    Boolean hasTeamRegion(String userId) throws TeamNotFoundException, UserNotFoundException;
    BaseResultDto setTeamRegion(String regionId,String userId) throws UserNotFoundException, TeamNotFoundException;
}
