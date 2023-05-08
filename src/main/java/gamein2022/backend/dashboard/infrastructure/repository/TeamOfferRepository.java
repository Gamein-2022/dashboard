package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.TeamOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamOfferRepository extends JpaRepository<TeamOffer, Long> {
    List<TeamOffer> findAllByUser_Id(Long userId);
    List<TeamOffer> findAllByTeam_Id(Long teamId);
    Optional<TeamOffer> findByTeam_IdAndUser_Id(Long teamId, Long userId);
}
