package gamein2022.backend.dashboard.infrastructure.service.team;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.*;
import gamein2022.backend.dashboard.core.sharedkernel.enums.LogType;
import gamein2022.backend.dashboard.infrastructure.repository.*;
import gamein2022.backend.dashboard.web.dto.result.*;
import gamein2022.backend.dashboard.web.iao.AuthInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TeamServiceHandler {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    private final TimeRepository timeRepository;

    private final LogRepository logRepository;

    private final BuildingRepository buildingRepository;

    private final StorageProductRepository storageProductRepository;

    private final BuildingInfoRepository buildingInfoRepository;

    private final TeamResearchRepository teamResearchRepository;



    public TeamServiceHandler(UserRepository userRepository, TeamRepository teamRepository, TimeRepository timeRepository, LogRepository logRepository, BuildingRepository buildingRepository, StorageProductRepository storageProductRepository, BuildingInfoRepository buildingInfoRepository, TeamResearchRepository teamResearchRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.timeRepository = timeRepository;
        this.logRepository = logRepository;
        this.buildingRepository = buildingRepository;
        this.storageProductRepository = storageProductRepository;
        this.buildingInfoRepository = buildingInfoRepository;
        this.teamResearchRepository = teamResearchRepository;
    }

    List<WealthDto> teamsWealth = new ArrayList<>();


    public RegionResultDTO getTeamRegion(long teamId) throws UserNotFoundException {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isEmpty())
            throw new UserNotFoundException();

        Time time = timeRepository.findById(1L).get();
        LocalDateTime endDate = time.getBeginTime().plusSeconds(time.getChooseRegionDuration());
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        Long remainingTime = Duration.between(now, endDate).toSeconds() - time.getStoppedTimeSeconds();
        Team team = teamOptional.get();
        RegionResultDTO regionResultDTO = new RegionResultDTO();
        regionResultDTO.setTeamRegionId(team.getRegion());
        regionResultDTO.setLastRegionId(team.getRegion());
        regionResultDTO.setRemainingTime(remainingTime);
        regionResultDTO.setTeamBalance(team.getBalance());
        return regionResultDTO;

    }

    public TeamInfoResultDTO getTeamInfo(Long teamId, Long userId) throws BadRequestException {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isEmpty()) throw new BadRequestException("team not found");
        Team team = teamOptional.get();
        return new TeamInfoResultDTO(team.getName(),
                team.getUsers().stream().map(User::toDTO).collect(Collectors.toList()),
                userId.equals(team.getOwner().getId()));
    }


    public GetTeamLogsResultDTO getTeamLogs(AuthInfo authInfo, LogType logType) {
        List<LogDTO> logs;

        if (logType.equals(LogType.DEFAULT)) {
            logs = logRepository.findAllByTeamId(authInfo.getTeamId())
                    .stream().map(Log::toDto).toList();
        } else {
            logs = logRepository.findAllByTypeAndTeamId(logType, authInfo.getTeamId())
                    .stream().map(Log::toDto).toList();
        }
        return new GetTeamLogsResultDTO(logs);
    }

    public Long getTeamWealth(Long teamId) {
        long wealth = 0L;
        Iterable<BuildingInfo> buildingInfos = buildingInfoRepository.findAll();
        Team team = teamRepository.findById(teamId).get();
        List<StorageProduct> teamsProduct = storageProductRepository.findAllByTeamId(teamId);
        for (StorageProduct storageProduct : teamsProduct) {
            wealth += storageProduct.getProduct().getMinPrice() * storageProduct.getInStorageAmount();
        }
        List<Building> teamBuildings = buildingRepository.findAllByTeamId(teamId);
        for (Building building : teamBuildings) {
            for (BuildingInfo buildingInfo : buildingInfos) {
                if (buildingInfo.getType().equals(building.getType())) {
                    wealth += buildingInfo.getBuildPrice();
                    wealth += building.isUpgraded() ? buildingInfo.getUpgradePrice() : 0;
                }
            }

        }
        List<TeamResearch> teamResearches = teamResearchRepository.findAllByTeamIdAndAndEndTimeAfter(teamId,
                LocalDateTime.now(ZoneOffset.UTC));
        for (TeamResearch teamResearch : teamResearches) {
            wealth += teamResearch.getPaidAmount();
        }
        wealth += team.getBalance();
        return wealth;
    }

    public GetTeamWealthResultDTO getTeamRank(Long teamId) {
        GetTeamWealthResultDTO resultDTO = new GetTeamWealthResultDTO();
        resultDTO.setLastTopWealth(-1L);
        resultDTO.setRank(-1L);
        for (int i = 0; i < teamsWealth.size(); i++) {
            WealthDto wealthDto = teamsWealth.get(i);
            if (wealthDto.getTeamId().equals(teamId)) {
                if (i <= 100)
                    resultDTO.setRank((long) i);
                resultDTO.setTeamWealth(wealthDto.getWealth());
                resultDTO.setBrand(wealthDto.getBrand());
                List<WealthDto> upper = new ArrayList<>();
                for (int j = 1; j < Math.min(4, i + 1); j++) {
                    upper.add(teamsWealth.get(i - j));
                }
                Collections.reverse(upper);
                resultDTO.setUpper(upper);
                List<WealthDto> lower = new ArrayList<>();
                for (int j = 1; j < Math.min(4, teamsWealth.size() - i); j++) {
                    lower.add(teamsWealth.get(i + j));
                }
                resultDTO.setLower(lower);

            }

            if (i == 100) {
                resultDTO.setLastTopWealth(wealthDto.getWealth());
            }
        }
        return resultDTO;
    }

    public List<WealthDto> getTop100() {
        return teamsWealth.subList(0, Math.min(teamsWealth.size(), 100));
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void updateRanks() {
        List<Team> teams = teamRepository.findAll();
        List<WealthDto> wealths = new ArrayList<>();
        for (Team team : teams) {
            if (team.getId() != 0){
                WealthDto wealthDto = new WealthDto();
                wealthDto.setTeamId(team.getId());
                wealthDto.setBrand(10L);
                //TODO fix mock brand
                wealthDto.setWealth(getTeamWealth(team.getId()));
                wealthDto.setTeamName(team.getName());
                wealths.add(wealthDto);
            }
        }
        wealths.sort(Comparator.comparing(WealthDto::getWealth).reversed());
        teamsWealth = wealths;
    }

}
