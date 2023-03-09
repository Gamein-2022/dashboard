package gamein2022.backend.dashboard.infrastructure.service.team;

import com.google.common.cache.Cache;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.TeamRepository;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.web.dto.result.BaseResultDTO;
import gamein2022.backend.dashboard.core.exception.notfound.TeamNotFoundException;
import gamein2022.backend.dashboard.core.exception.notfound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamServiceHandler implements TeamService {
    @Autowired
    @Qualifier("teamLogCache")
    Cache<String, Team> teamLog;

    @Autowired
    @Qualifier("userLogCache")
    Cache<String, User> userLog;

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    private User getUser(Long userId) throws UserNotFoundException {
        User user = userLog.getIfPresent(userId);
        if (user == null) {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) user = userOptional.get();
            else throw new UserNotFoundException();
        }
        return user;
    }


    public TeamServiceHandler(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Boolean hasTeamRegion(Long userId) throws TeamNotFoundException, UserNotFoundException {
        User user = getUser(userId);
        return user.getTeam().getRegion() != 0;
    }

    @Override
    public BaseResultDTO setTeamRegion(int regionId, Long userId) throws UserNotFoundException, TeamNotFoundException {
        User user = getUser(userId);
        Team team = user.getTeam();
        team.setRegion(regionId);
        teamRepository.save(team);
        return new BaseResultDTO(true, "region set");
    }


}
