package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class StorageProductDTO {
    private ProductDTO product;
    private long inStorageAmount;
    private long inQueueAmount;
    private long inRouteAmount;
    private long manufacturingAmount;
}
