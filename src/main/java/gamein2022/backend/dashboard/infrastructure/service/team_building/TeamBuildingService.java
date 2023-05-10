package gamein2022.backend.dashboard.infrastructure.service.team_building;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UnauthorizedException;
import gamein2022.backend.dashboard.web.dto.result.ProfileInfoResultDTO;
import gamein2022.backend.dashboard.web.dto.result.TeamInfoResultDTO;
import gamein2022.backend.dashboard.web.dto.result.TeamOfferDTO;
import gamein2022.backend.dashboard.web.dto.result.UserDTO;

import java.util.List;


public interface TeamBuildingService {
    List<UserDTO> getUsers(Long userId);
    TeamOfferDTO requestTeamJoin(Long team, Long owner, Long userId) throws UnauthorizedException, BadRequestException;
    List<TeamOfferDTO> getMyOffers(Long userId);
    List<TeamOfferDTO> getTeamOffers(Long teamId, Long ownerId) throws BadRequestException, UnauthorizedException;
    TeamInfoResultDTO acceptOffer(Long userId, Long offerId) throws BadRequestException;
    ProfileInfoResultDTO leaveTeam(Long userId) throws BadRequestException;
}
