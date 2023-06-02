package gamein2022.backend.dashboard.web.dto.result;

import gamein2022.backend.dashboard.core.sharedkernel.enums.ShippingMethod;
import gamein2022.backend.dashboard.core.sharedkernel.enums.ShippingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.sql.Timestamp;


@AllArgsConstructor
@Getter
public class ShippingDTO {
    private Long id;
    private Integer sourceRegion;
    private Long teamId;
    private ShippingMethod method;
    private ShippingStatus status;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private Timestamp currentTime;
    private ProductDTO product;
    private int amount;
    private boolean collectable;
}
