package gamein2022.backend.dashboard.infrastructure.service.team_building;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UnauthorizedException;
import gamein2022.backend.dashboard.web.dto.result.TeamOfferDTO;
import gamein2022.backend.dashboard.web.dto.result.UserDTO;

import java.util.List;


public interface TeamBuildingService {
    List<UserDTO> getUsers();

    TeamOfferDTO requestTeamJoin(Long team, Long owner, Long userId) throws UnauthorizedException, BadRequestException;

    List<TeamOfferDTO> getMyOffers(Long userId);
}
