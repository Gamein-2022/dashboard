package gamein2022.backend.dashboard.data.repository;

import gamein2022.backend.dashboard.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
