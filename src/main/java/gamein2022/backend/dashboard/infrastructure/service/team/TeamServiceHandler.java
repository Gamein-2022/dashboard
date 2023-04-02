package gamein2022.backend.dashboard.infrastructure.service.team;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.TeamRepository;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.web.dto.result.RegionResultDTO;
import gamein2022.backend.dashboard.web.dto.result.TeamInfoResultDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TeamServiceHandler {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;



    public TeamServiceHandler(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    public RegionResultDTO setTeamRegion(long teamId,String teamRegion) throws UserNotFoundException {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isEmpty())
            throw new UserNotFoundException();
        Team team = teamOptional.get();
        RegionResultDTO regionResultDTO = new RegionResultDTO();
        regionResultDTO.setLastRegionId(team.getRegion());
        team.setRegion(Integer.parseInt(teamRegion));
        regionResultDTO.setTeamRegionId(team.getRegion());
        teamRepository.save(team);
        return regionResultDTO;
    }

    public RegionResultDTO getTeamRegion(long teamId) throws UserNotFoundException {
         Optional<Team> teamOptional = teamRepository.findById(teamId);
         if (teamOptional.isEmpty())
             throw new UserNotFoundException();
         Team team = teamOptional.get();
         RegionResultDTO regionResultDTO = new RegionResultDTO();
         regionResultDTO.setTeamRegionId(team.getRegion());
         regionResultDTO.setLastRegionId(team.getRegion());
         return regionResultDTO;
    }
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

        user.setTeam(team);

        userRepository.save(user);

        return new TeamInfoResultDTO(team.getName());
    }
}