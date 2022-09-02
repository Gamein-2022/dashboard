package gamein2022.backend.dashboard.service.team;

import com.google.common.cache.Cache;
import gamein2022.backend.dashboard.data.dto.result.BaseResultDto;
import gamein2022.backend.dashboard.data.entity.Team;
import gamein2022.backend.dashboard.data.entity.User;
import gamein2022.backend.dashboard.data.repository.TeamRepository;
import gamein2022.backend.dashboard.data.repository.UserRepository;
import gamein2022.backend.dashboard.exception.notfound.TeamNotFoundException;
import gamein2022.backend.dashboard.exception.notfound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamServiceHandler implements TeamService {
    @Autowired
    @Qualifier("teamLogCache")
    Cache<String,Team> teamLog;


    @Autowired
    @Qualifier("userLogCache")
    Cache<String,User> userLog;

    private TeamRepository teamRepository;
    private UserRepository userRepository;

    private User getUser(String userId) throws UserNotFoundException {
        User user = userLog.getIfPresent(userId);
        if (user == null){
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) user = userOptional.get();
            else throw new UserNotFoundException();
        }
        return user;
    }
    private Team getTeam(String teamId) throws TeamNotFoundException {
        Team team = teamLog.getIfPresent(teamId);
        if (team == null){
            Optional<Team> teamOptional = teamRepository.findById(teamId);
            if (teamOptional.isPresent()) team = teamOptional.get();
            else throw new TeamNotFoundException();
        }
        return team;
    }


    public TeamServiceHandler(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Boolean hasTeamRegion(String userId) throws TeamNotFoundException, UserNotFoundException {
        User user = getUser(userId);
        Team team = getTeam(user.getTeamId());
        return team.getTeamRegionId() != null;
    }

    @Override
    public BaseResultDto setTeamRegion(String regionId, String userId) throws UserNotFoundException, TeamNotFoundException {
        User user = getUser(userId);
        Team team = getTeam(user.getTeamId());
        team.setTeamRegionId(regionId);
        teamRepository.save(team);
        return new BaseResultDto(true,"region set");
    }


}
