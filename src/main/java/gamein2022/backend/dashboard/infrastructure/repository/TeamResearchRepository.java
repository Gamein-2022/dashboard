package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.TeamResearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TeamResearchRepository extends JpaRepository<TeamResearch,Long> {
    List<TeamResearch> findAllByTeamIdAndAndEndTimeAfter(Long teamId, Date endTime);

    @Modifying
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Query(value = "UPDATE team_researches SET end_time = end_time + make_interval(0, 0, 0, 0, 0, 0, :duration)", nativeQuery =
            true)
    void updateRAndDEndTime(Long duration);

    @Modifying
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Query(value = "UPDATE team_researches SET begin_time = begin_time + make_interval(0, 0, 0, 0, 0, 0, :duration)",
            nativeQuery = true)
    void updateRAndDBeginTime(Long duration);

    List<TeamResearch> findAllByTeamIdAndAndEndTimeBefore(Long teamId, Date endTime);
}
