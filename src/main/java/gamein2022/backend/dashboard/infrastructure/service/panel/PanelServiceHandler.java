package gamein2022.backend.dashboard.infrastructure.service.panel;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Time;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.TeamRepository;
import gamein2022.backend.dashboard.infrastructure.repository.TimeRepository;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.infrastructure.service.auth.AuthService;
import gamein2022.backend.dashboard.infrastructure.service.team.TeamServiceHandler;
import gamein2022.backend.dashboard.infrastructure.util.JwtUtils;
import gamein2022.backend.dashboard.web.dto.result.GetTop100Result;
import gamein2022.backend.dashboard.web.dto.result.RegisterAndLoginResultDTO;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PanelServiceHandler implements PanelService {

    private final TeamRepository teamRepository;
    private final TimeRepository timeRepository;

    private final AuthService authService;

    private final UserRepository userRepository;

    private final TeamServiceHandler teamService;

    public PanelServiceHandler(TeamRepository teamRepository, TimeRepository timeRepository, AuthService authService, UserRepository userRepository, TeamServiceHandler teamService) {
        this.teamRepository = teamRepository;
        this.timeRepository = timeRepository;
        this.authService = authService;
        this.userRepository = userRepository;
        this.teamService = teamService;
    }

    @Override
    public void addBalanceToAllTeams(Long addBalance) {
        List<Team> teams = teamRepository.findAll();
        for (Team team : teams) {
            team.setBalance(team.getBalance() + addBalance);
        }
        teamRepository.saveAll(teams);
    }

    @Override
    public void restartGame() {
        Time time = timeRepository.findById(1L).get();
        time.setBeginTime(LocalDateTime.now(ZoneOffset.UTC));
        time.setStoppedTimeSeconds(0L);
        time.setLastStopTime(null);
        time.setIsGamePaused(false);
        time.setIsRegionPayed(false);
        timeRepository.save(time);
    }

    @Override
    public void pauseGame() {
        Time time = timeRepository.findById(1L).get();
        time.setLastStopTime(LocalDateTime.now(ZoneOffset.UTC));
        time.setIsGamePaused(true);
        timeRepository.save(time);
    }

    @Override
    public void resumeGame() {
        Time time = timeRepository.findById(1L).get();
        Long duration = Duration.between(time.getLastStopTime(), LocalDateTime.now(ZoneOffset.UTC)).toSeconds();
        time.setLastStopTime(null);
        time.setStoppedTimeSeconds(time.getStoppedTimeSeconds() + duration);
        time.setIsGamePaused(false);
        timeRepository.save(time);
    }

    @Override
    public GetTop100Result getTop100() {
        GetTop100Result getTop100Result = new GetTop100Result();
        getTop100Result.setTopTeams(teamService.getTop100());
        return getTop100Result;
    }

    @Override
    public void addTeam(String username, String password, String teamName) throws UserNotFoundException, BadRequestException, InvalidTokenException {
        User user = login(username, username, password);
        if (user.getTeam() != null)
            throw new BadRequestException("کاربر در حال حاضر دارای تیم می باشد.");
        Optional<Team> teamOptional = teamRepository.findTeamByName(teamName);
        if (teamOptional.isPresent()){
            Team team = teamOptional.get();
            team.getUsers().add(user);
            teamRepository.save(team);
            user.setTeam(team);
            userRepository.save(user);
        }else {
            Team team = new Team();
            team.setName(teamName);
            team.setUsers(new ArrayList<>());
            team.getUsers().add(user);
            team.setOwner(user);
            team.setBalance(212_000_000);
            teamRepository.save(team);
            user.setTeam(team);
            userRepository.save(user);
        }
    }

    private User login(String email, String phone, String password)
            throws UserNotFoundException, BadRequestException {
        if (
                (email == null && phone == null)
                        || ((email != null && email.isEmpty()) && (phone != null && phone.isEmpty()))
        ) {
            throw new BadRequestException("اطلاعات وارد شده صحیح نمی باشد");
        }

        if (password == null || password.length() < 8) {
            throw new BadRequestException("اطلاعات وارد شده صحیح نمی باشد");
        }

        Optional<User> userOptional = userRepository.findByEmailOrPhone(email, phone);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("اطلاعات وارد شده صحیح نمی باشد");
        }

        User user = userOptional.get();

        if ((new BCryptPasswordEncoder()).matches(password, user.getPassword())) {
            return user;
        }
        throw new UserNotFoundException();
    }


}
