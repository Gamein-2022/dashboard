package gamein2022.backend.dashboard.infrastructure.service.team;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.*;
import gamein2022.backend.dashboard.core.sharedkernel.enums.LogType;
import gamein2022.backend.dashboard.infrastructure.repository.*;
import gamein2022.backend.dashboard.web.dto.result.*;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import gamein2022.backend.dashboard.web.iao.BuildingInfo;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<LogDTO> firstList = logRepository.findAllByTypeAndTeamId(LogType.ASSEMBLY,authInfo.getTeamId())
                .stream().map(Log::toDto).toList();;
        List<LogDTO> secList = new ArrayList<>(logRepository.findAllByTypeAndTeamId(LogType.PRODUCTION, authInfo.getTeamId())
                .stream().map(Log::toDto).toList());
        secList.addAll(firstList);
        return new GetTeamLogsResultDTO(secList);
    }

    public GetTeamWealthResultDTO getTeamWealth(Long teamId){
        long wealth = 0L;
        Team team = teamRepository.findById(teamId).get();
        List<StorageProduct> teamsProduct = storageProductRepository.findAllByTeamId(teamId);
        for (StorageProduct storageProduct : teamsProduct){
            wealth += storageProduct.getProduct().getPrice() * storageProduct.getInStorageAmount();
        }
        List<Building> teamBuildings = buildingRepository.findAllByTeamId(teamId);
        for (Building building : teamBuildings){
            wealth += BuildingInfo.getInfo(building.getType()).getBuildPrice();
        }
        wealth += team.getBalance();
        return new GetTeamWealthResultDTO(wealth);
    }
}
