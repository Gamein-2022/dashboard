package gamein2022.backend.dashboard.data.repository;

import gamein2022.backend.dashboard.data.entity.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team,String> {

}
