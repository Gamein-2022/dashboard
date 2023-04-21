package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.Log;
import gamein2022.backend.dashboard.core.sharedkernel.enums.LogType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogRepository extends CrudRepository<Log,Long> {
    List<Log> findAllByTeamId(Long teamId);
    List<Log>findAllByTypeAndTeamId(LogType logType,Long teamId);
}
