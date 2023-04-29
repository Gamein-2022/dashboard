package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT COUNT  (*) FROM Team ")
    Integer getCount();
}