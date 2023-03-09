package gamein2022.backend.dashboard.infrastructure.service.team;

import gamein2022.backend.dashboard.web.dto.result.BaseResultDto;
import gamein2022.backend.dashboard.core.exception.notfound.TeamNotFoundException;
import gamein2022.backend.dashboard.core.exception.notfound.UserNotFoundException;

public interface TeamService {
    Boolean hasTeamRegion(Long userId) throws TeamNotFoundException, UserNotFoundException;
    BaseResultDto setTeamRegion(int regionId,Long userId) throws UserNotFoundException, TeamNotFoundException;
}
