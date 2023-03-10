package gamein2022.backend.dashboard.infrastructure.service.profile;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.TeamRepository;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.web.dto.request.ProfileInfoRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.ProfileInfoResultDTO;
import gamein2022.backend.dashboard.web.dto.result.TeamInfoResultDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProfileServiceHandler implements ProfileService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public ProfileServiceHandler(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public ProfileInfoResultDTO getProfileInfo(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userOptional.get();

        return new ProfileInfoResultDTO(user.getEnglishName(), user.getPersianName());
    }

    @Override
    public ProfileInfoResultDTO setProfileInfo(Long id, ProfileInfoRequestDTO info) throws UserNotFoundException, BadRequestException {
        if (info.getEnglishName() == null && info.getPersianName() == null) {
            throw new BadRequestException();
        }

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userOptional.get();

        if (info.getEnglishName() != null) {
            user.setEnglishName(info.getEnglishName());
        }
        if (info.getPersianName() != null) {
            user.setPersianName(info.getPersianName());
        }

        return new ProfileInfoResultDTO(user.getEnglishName(), user.getPersianName());
    }

    @Override
    public TeamInfoResultDTO createTeam(Long userId, String teamName) throws UserNotFoundException, BadRequestException {
        if (teamName == null || teamName.isEmpty()) {
            throw new BadRequestException();
        }

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userOptional.get();

        Team team = new Team();

        team.setName(teamName);
        team.setUsers(new ArrayList<>());
        team.getUsers().add(user);
        team.setOwner(user);

        teamRepository.save(team);

        return new TeamInfoResultDTO(team.getName());
    }
}
