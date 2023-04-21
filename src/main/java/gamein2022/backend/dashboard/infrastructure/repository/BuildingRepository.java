package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.Building;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BuildingRepository extends CrudRepository<Building,Long> {
    List<Building> findAllByTeamId(Long teamId);
}
