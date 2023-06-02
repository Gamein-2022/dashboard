package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.Shipping;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface ShippingRepository extends CrudRepository<Shipping,Long> {
    @Modifying
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Query(value = "UPDATE shipping SET departureTime = departureTime + make_interval(0, 0, 0, 0, 0, 0, :duration)",
            nativeQuery = true)
    void updateShippingEndTime(Long duration);

    @Modifying
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Query(value = "UPDATE shipping SET arrivalTime = arrivalTime + make_interval(0, 0, 0, 0, 0, 0, :duration)",
            nativeQuery = true)
    void updateShippingBeginTime(Long duration);
}
