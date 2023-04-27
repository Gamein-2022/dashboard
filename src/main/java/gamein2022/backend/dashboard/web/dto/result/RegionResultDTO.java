package gamein2022.backend.dashboard.web.dto.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionResultDTO implements BaseResultDTO{
    private int teamRegionId;
    private int lastRegionId;
    private Long remainingTime;
    private Long teamBalance;
}
