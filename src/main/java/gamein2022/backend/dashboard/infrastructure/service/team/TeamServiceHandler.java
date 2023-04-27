package gamein2022.backend.dashboard.infrastructure.service.team;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.*;
import gamein2022.backend.dashboard.core.sharedkernel.enums.LogType;
import gamein2022.backend.dashboard.infrastructure.repository.*;
import gamein2022.backend.dashboard.web.dto.result.*;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import gamein2022.backend.dashboard.web.iao.BuildingInfo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TeamServiceHandler {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    private final TimeRepository timeRepository;

    private final LogRepository logRepository;

    private final BuildingRepository buildingRepository;

    private final StorageProductRepository storageProductRepository;


    public TeamServiceHandler(UserRepository userRepository, TeamRepository teamRepository, TimeRepository timeRepository, LogRepository logRepository, BuildingRepository buildingRepository, StorageProductRepository storageProductRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.timeRepository = timeRepository;
        this.logRepository = logRepository;
        this.buildingRepository = buildingRepository;
        this.storageProductRepository = storageProductRepository;
    }

    List<WealthDto> teamsWealth = new ArrayList<>();

    public RegionResultDTO setTeamRegion(long teamId, String teamRegion) throws UserNotFoundException {
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

        Time time = timeRepository.findById(1L).get();
        LocalDateTime endDate = time.getBeginTime().plusSeconds(time.getChooseRegionDuration());
        LocalDateTime now = LocalDateTime.now();
        Long remainingTime = Duration.between(now, endDate).toSeconds();
        Team team = teamOptional.get();
        RegionResultDTO regionResultDTO = new RegionResultDTO();
        regionResultDTO.setTeamRegionId(team.getRegion());
        regionResultDTO.setLastRegionId(team.getRegion());
        regionResultDTO.setRemainingTime(remainingTime);
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
        team.setBalance(1000000000);

        teamRepository.save(team);

        user.setTeam(team);

        userRepository.save(user);

        return new TeamInfoResultDTO(team.getName());
    }

    public GetTeamLogsResultDTO getTeamLogs(AuthInfo authInfo) {
        List<LogDTO> firstList = logRepository.findAllByTypeAndTeamId(LogType.ASSEMBLY, authInfo.getTeamId())
                .stream().map(Log::toDto).toList();
        ;
        List<LogDTO> secList = new ArrayList<>(logRepository.findAllByTypeAndTeamId(LogType.PRODUCTION, authInfo.getTeamId())
                .stream().map(Log::toDto).toList());
        secList.addAll(firstList);
        return new GetTeamLogsResultDTO(secList);
    }

    public Long getTeamWealth(Long teamId) {
        long wealth = 0L;
        Team team = teamRepository.findById(teamId).get();
        List<StorageProduct> teamsProduct = storageProductRepository.findAllByTeamId(teamId);
        for (StorageProduct storageProduct : teamsProduct) {
            wealth += storageProduct.getProduct().getPrice() * storageProduct.getInStorageAmount();
        }
        List<Building> teamBuildings = buildingRepository.findAllByTeamId(teamId);
        for (Building building : teamBuildings) {
            wealth += BuildingInfo.getInfo(building.getType()).getBuildPrice();
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
                for (int j = 1; j < Math.min(4, teamsWealth.size() - 1 - j); j++){
                    lower.add(teamsWealth.get(i + j));
                }
                resultDTO.setLower(lower);

            }

            if (i == 100){
                resultDTO.setLastTopWealth(wealthDto.getWealth());
            }
        }
        return resultDTO;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void updateRanks() {
        List<Team> teams = teamRepository.findAll();
        List<WealthDto> wealths = new ArrayList<>();
        for (Team team : teams) {
            WealthDto wealthDto = new WealthDto();
            wealthDto.setTeamId(team.getId());
            wealthDto.setBrand(10L);
            //TODO fix mock brand
            wealthDto.setWealth(getTeamWealth(team.getId()));
            wealths.add(wealthDto);
        }
        wealths.sort(Comparator.comparing(WealthDto::getWealth));
        teamsWealth = wealths;
    }


}
