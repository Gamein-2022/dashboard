package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.StorageProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StorageProductRepository extends CrudRepository<StorageProduct,Long> {
    List<StorageProduct> findAllByTeamId(Long teamId);

}
