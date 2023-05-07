package gamein2022.backend.dashboard.infrastructure.service.team_building;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UnauthorizedException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import gamein2022.backend.dashboard.core.sharedkernel.entity.TeamOffer;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.TeamOfferRepository;
import gamein2022.backend.dashboard.infrastructure.repository.TeamRepository;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.web.dto.result.TeamOfferDTO;
import gamein2022.backend.dashboard.web.dto.result.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TeamBuildingServiceHandler implements TeamBuildingService {
    private final UserRepository userRepository;
    private final TeamOfferRepository teamOfferRepository;
    private final TeamRepository teamRepository;

    public TeamBuildingServiceHandler(UserRepository userRepository, TeamOfferRepository teamOfferRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamOfferRepository = teamOfferRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream().map(User::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeamOfferDTO requestTeamJoin(Long teamId, Long ownerId, Long userId) throws UnauthorizedException,
            BadRequestException {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isEmpty()) {
            throw new BadRequestException("شما تیمی ندارید!");
        }
        Team team = teamOptional.get();
        if (!team.getOwner().getId().equals(ownerId)) {
            throw new UnauthorizedException("شما اجازه‌ی این کار را ندارید!");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new BadRequestException("کاربر درخواست شده وجود ندارد!");
        }

        TeamOffer offer = new TeamOffer();
        offer.setTeam(team);
        offer.setUser(userOptional.get());
        return teamOfferRepository.save(offer).toDTO();
    }

    @Override
    public List<TeamOfferDTO> getMyOffers(Long userId) {
        return teamOfferRepository.findAllByUser_Id(userId).stream().map(TeamOffer::toDTO).collect(Collectors.toList());
    }
}
