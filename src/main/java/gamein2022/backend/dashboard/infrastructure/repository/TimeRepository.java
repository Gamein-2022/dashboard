package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.Time;
import org.springframework.data.repository.CrudRepository;

public interface TimeRepository extends CrudRepository<Time,Long> {
}
