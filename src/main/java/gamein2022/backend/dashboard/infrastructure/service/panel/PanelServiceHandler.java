package gamein2022.backend.dashboard.infrastructure.service.panel;

import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Time;
import gamein2022.backend.dashboard.infrastructure.repository.TeamRepository;
import gamein2022.backend.dashboard.infrastructure.repository.TimeRepository;
import gamein2022.backend.dashboard.infrastructure.service.team.TeamService;
import gamein2022.backend.dashboard.infrastructure.service.team.TeamServiceHandler;
import gamein2022.backend.dashboard.web.dto.result.GetTop100Result;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class PanelServiceHandler implements PanelService{

    private final TeamRepository teamRepository;
    private final TimeRepository timeRepository;

    private final TeamServiceHandler teamService;

    public PanelServiceHandler(TeamRepository teamRepository, TimeRepository timeRepository, TeamServiceHandler teamService) {
        this.teamRepository = teamRepository;
        this.timeRepository = timeRepository;
        this.teamService = teamService;
    }

    @Override
    public void addBalanceToAllTeams(Long addBalance) {
        List<Team> teams= teamRepository.findAll();
        for (Team team : teams){
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
        Long duration = Duration.between(time.getLastStopTime(),LocalDateTime.now(ZoneOffset.UTC)).toSeconds();
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


}
