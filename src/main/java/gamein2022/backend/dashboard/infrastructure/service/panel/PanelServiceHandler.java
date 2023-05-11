package gamein2022.backend.dashboard.infrastructure.service.panel;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.News;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Time;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.core.sharedkernel.enums.NewsType;
import gamein2022.backend.dashboard.infrastructure.repository.*;
import gamein2022.backend.dashboard.infrastructure.service.team.TeamServiceHandler;
import gamein2022.backend.dashboard.web.dto.result.GetTop100Result;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PanelServiceHandler implements PanelService {

    private final TeamRepository teamRepository;
    private final TimeRepository timeRepository;
    private final UserRepository userRepository;
    private final TeamServiceHandler teamService;
    private final NewsRepository newsRepository;

    public PanelServiceHandler(TeamRepository teamRepository, TimeRepository timeRepository, UserRepository userRepository, TeamServiceHandler teamService, NewsRepository newsRepository) {
        this.teamRepository = teamRepository;
        this.timeRepository = timeRepository;
        this.userRepository = userRepository;
        this.teamService = teamService;
        this.newsRepository = newsRepository;
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
//        teamRepository.resetTeamsRegion();
//        regionRepository.resetAllRegions();
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
            team.setRegion(new Random().nextInt(8) + 1);
            team.setUsers(new ArrayList<>());
            team.getUsers().add(user);
            team.setOwner(user);
            teamRepository.save(team);
            user.setTeam(team);
            userRepository.save(user);
        }
    }

    @Override
    public void addNews(String title, String desc, String image, NewsType type, String date) {
        News news = new News();
        news.setTitle(title);
        news.setDesc(desc);
        news.setImage(image);
        news.setType(type);
        news.setDate(date);
        newsRepository.save(news);
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
