package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.TeamResearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TeamResearchRepository extends JpaRepository<TeamResearch,Long> {
    List<TeamResearch> findAllByTeamIdAndAndEndTimeBefore(Long teamId, Date endTime);
}
