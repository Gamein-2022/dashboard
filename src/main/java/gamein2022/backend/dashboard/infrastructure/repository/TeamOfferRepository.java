package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.TeamOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamOfferRepository extends JpaRepository<TeamOffer, Long> {
    List<TeamOffer> findAllByUser_Id(Long userId);
}
