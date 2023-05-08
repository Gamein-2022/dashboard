package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT COUNT  (*) FROM Team ")
    Integer getCount();

    @Modifying
    @Query(value = "update teams set region = 0",nativeQuery = true)
    void resetTeamsRegion();

    Optional<Team> findTeamByName(String name);

}