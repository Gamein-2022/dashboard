package gamein2022.backend.dashboard.infrastructure.service.profile;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.web.dto.request.ProfileInfoRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.ProfileInfoResultDTO;
import gamein2022.backend.dashboard.web.dto.result.TeamInfoResultDTO;

public interface ProfileService {
    ProfileInfoResultDTO getProfileInfo(Long id) throws UserNotFoundException;
    ProfileInfoResultDTO setProfileInfo(Long id, ProfileInfoRequestDTO info) throws UserNotFoundException, BadRequestException;
    TeamInfoResultDTO createTeam (Long userId, String teamName) throws UserNotFoundException, BadRequestException;
}
