package gamein2022.backend.dashboard.infrastructure.service.team;

import gamein2022.backend.dashboard.web.dto.result.BaseResultDto;
import gamein2022.backend.dashboard.core.exception.notfound.TeamNotFoundException;
import gamein2022.backend.dashboard.core.exception.notfound.UserNotFoundException;

public interface TeamService {
    Boolean hasTeamRegion(String userId) throws TeamNotFoundException, UserNotFoundException;
    BaseResultDto setTeamRegion(int regionId,String userId) throws UserNotFoundException, TeamNotFoundException;
}
