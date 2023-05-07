package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.BuildingInfo;
import gamein2022.backend.dashboard.core.sharedkernel.enums.BuildingType;
import org.springframework.data.repository.CrudRepository;

public interface BuildingInfoRepository extends CrudRepository<BuildingInfo, BuildingType> {
}
