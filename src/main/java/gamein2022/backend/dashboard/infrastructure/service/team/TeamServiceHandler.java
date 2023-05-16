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

    private final RegionRepository regionRepository;

    private final BuildingInfoRepository buildingInfoRepository;

    private final TeamResearchRepository teamResearchRepository;

    @Value("${live.data.url}")
    private String liveUrl;


    public TeamServiceHandler(UserRepository userRepository, TeamRepository teamRepository, TimeRepository timeRepository, LogRepository logRepository, BuildingRepository buildingRepository, StorageProductRepository storageProductRepository, RegionRepository regionRepository, BuildingInfoRepository buildingInfoRepository, TeamResearchRepository teamResearchRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.timeRepository = timeRepository;
        this.logRepository = logRepository;
        this.buildingRepository = buildingRepository;
        this.storageProductRepository = storageProductRepository;
        this.regionRepository = regionRepository;
        this.buildingInfoRepository = buildingInfoRepository;
        this.teamResearchRepository = teamResearchRepository;
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
        team.setBalance(212_000_000);

        teamRepository.save(team);

        user.setTeam(team);

        userRepository.save(user);

        return new TeamInfoResultDTO(team.getName(),
                team.getUsers().stream().map(User::toDTO).collect(Collectors.toList()),
                true);
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

//    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
//    public void payRegionPrice() {
//        Time time = timeRepository.findById(1L).get();
//        Long duration =  Duration.between(time.getBeginTime(),LocalDateTime.now(ZoneOffset.UTC)).toSeconds();
//        boolean isChooseRegionFinished = duration - time.getStoppedTimeSeconds() > time.getChooseRegionDuration();
//        if (! time.getIsRegionPayed() && isChooseRegionFinished){
//            List<Region> regions = regionRepository.findAll();
//            List<Team> teams = teamRepository.findAll();
//            for (Team team : teams){
//                if (team.getRegion() == 0){
//                    Random random = new Random();
//                    team.setRegion(random.nextInt(8) + 1);
//                    Region region = regions.get(team.getRegion() - 1);
//                    region.setRegionPopulation(region.getRegionPopulation() + 1);
//                }
//            }
//            for (Region region: regions){
//                region.setRegionPayed(calculateRegionPrice(region.getRegionPopulation()));
//            }
//            for (Team team : teams){
//                team.setBalance(team.getBalance() - regions.get(team.getRegion() - 1).getRegionPayed());
//            }
//            regionRepository.saveAll(regions);
//            teamRepository.saveAll(teams);
//            time.setIsRegionPayed(true);
//            timeRepository.save(time);
//            String text = "هزینه زمین از حساب شما برداشت شد.";
//            RestUtil.sendNotificationToAll(text,"UPDATE_BALANCE",liveUrl);
//        }
//    }

    private Long calculateRegionPrice(Long currentPopulation) {
        Time time = timeRepository.findById(1L).get();
        Long scale = time.getScale();
        Integer teamsCount = teamRepository.getCount();
        return (long) ((1 + (2.25 / (0.8 + 9 * Math.exp(-0.8 * (16 * currentPopulation / (teamsCount - 0.26)))))) * scale);
    }
}
