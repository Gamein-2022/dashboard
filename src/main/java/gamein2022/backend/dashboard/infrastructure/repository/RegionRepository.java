package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RegionRepository extends JpaRepository<Region,Long> {
    @Query("UPDATE Region SET regionPopulation = 0 where true ")
    void resetAllRegions();
}
