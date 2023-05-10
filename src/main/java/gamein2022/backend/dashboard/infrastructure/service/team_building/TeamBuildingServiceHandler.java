package gamein2022.backend.dashboard.infrastructure.service.team_building;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UnauthorizedException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import gamein2022.backend.dashboard.core.sharedkernel.entity.TeamOffer;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.TeamOfferRepository;
import gamein2022.backend.dashboard.infrastructure.repository.TeamRepository;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.web.dto.result.ProfileInfoResultDTO;
import gamein2022.backend.dashboard.web.dto.result.TeamInfoResultDTO;
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
    public List<UserDTO> getUsers(Long userId) {
        return userRepository.findAllByIdNot(userId).stream().map(User::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeamOfferDTO requestTeamJoin(Long teamId, Long ownerId, Long userId) throws UnauthorizedException,
            BadRequestException {
        Team team = validateTeamAccess(teamId, ownerId);
        if (userId.equals(ownerId)) {
            throw new BadRequestException("شما نمی‌توانید به خود درخواست بدهید!");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new BadRequestException("کاربر درخواست شده وجود ندارد!");
        }
        Optional<TeamOffer> offerOptional = teamOfferRepository.findByTeam_IdAndUser_Id(team.getId(),
                userOptional.get().getId());
        if (offerOptional.isPresent()) {
            throw new BadRequestException("شما قبلا به این کاربر درخواست داده‌اید!");
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

    @Override
    public List<TeamOfferDTO> getTeamOffers(Long teamId, Long ownerId) throws BadRequestException, UnauthorizedException {
        validateTeamAccess(teamId, ownerId);
        return teamOfferRepository.findAllByTeam_Id(teamId).stream().map(TeamOffer::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeamInfoResultDTO acceptOffer(Long userId, Long offerId) throws BadRequestException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new BadRequestException("کاربر یافت نشد!");
        }
        Optional<TeamOffer> teamOfferOptional = teamOfferRepository.findById(offerId);
        if (teamOfferOptional.isEmpty()) {
            throw new BadRequestException("درخواست اضافه شدن به تیم یافت نشد!");
        }
        TeamOffer offer = teamOfferOptional.get();
        if (!userId.equals(offer.getUser().getId())) {
            throw new BadRequestException("درخواست اضافه شدن به تیم یافت نشد!");
        }
        User user = userOptional.get();
        if (user.getTeam() != null) {
            throw new BadRequestException("شما تیم دارید!");
        }
        Team team = offer.getTeam();
        if (team.getUsers().size() >= 3) {
            throw new BadRequestException("ظرفیت این تیم تکمیل است!");
        }
        team.getUsers().add(user);
        user.setTeam(team);
        teamRepository.save(team);
        userRepository.save(user);
        return new TeamInfoResultDTO(team.getName(),
                team.getUsers().stream().map(User::toDTO).collect(Collectors.toList()),
                false);
    }

    @Override
    public ProfileInfoResultDTO leaveTeam(Long userId) throws BadRequestException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new BadRequestException("شما وجود ندارید!");
        }
        User user = userOptional.get();
        Team team = user.getTeam();
        if (team == null) {
            throw new BadRequestException("شما تیم ندارید!");
        }
        if (userId.equals(team.getOwner().getId())) {
            team.getUsers().forEach(u -> {
                u.setTeam(null);
            });
            userRepository.saveAll(team.getUsers());
        } else {
            team.setUsers(team.getUsers().stream().filter(u -> u.getId().equals(userId)).toList());
            user.setTeam(null);
            teamRepository.save(team);
            userRepository.save(user);
        }
        return new ProfileInfoResultDTO(user.getEnglishName(), user.getPersianName());
    }

    private Team validateTeamAccess(Long teamId, Long ownerId) throws BadRequestException, UnauthorizedException {
        if (teamId == null) {
            throw new BadRequestException("شما تیمی ندارید!");
        }
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isEmpty()) {
            throw new BadRequestException("شما تیمی ندارید!");
        }
        Team team = teamOptional.get();
        if (!team.getOwner().getId().equals(ownerId)) {
            throw new UnauthorizedException("شما اجازه‌ی این کار را ندارید!");
        }
        return team;
    }
}
