package gamein2022.backend.dashboard.web.dto.result;

import gamein2022.backend.dashboard.core.sharedkernel.enums.BuildingType;
import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public class BuildingDTO {
    private long id;
    private BuildingType type;
    private boolean upgraded;
}
