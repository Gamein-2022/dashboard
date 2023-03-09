package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);
}