package gamein2022.backend.dashboard.web.iao;

import gamein2022.backend.dashboard.core.sharedkernel.enums.BuildingType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuildingInfo {
    private int baseLineCount;
    private int upgradedLineCount;
    private int buildPrice;
    private int upgradePrice;
    private boolean upgradable;

    public static BuildingInfo getInfo(BuildingType type) {
        switch (type) {
            case PRODUCTION_FACTORY:
                return new BuildingInfo(
                        2, 4, 85_000_000, 75_000_000, true
                );
            case ASSEMBLY_FACTORY:
                return new BuildingInfo(
                        3, 4, 85_000_000, 75_000_000, true
                );
            case RECYCLE_FACTORY:
                return new BuildingInfo(
                        0, 0, 10_000, 0, false
                );
            default:
                return new BuildingInfo(
                        0, 0, 80_000_000, 0, false
                );
        }
    }
}
