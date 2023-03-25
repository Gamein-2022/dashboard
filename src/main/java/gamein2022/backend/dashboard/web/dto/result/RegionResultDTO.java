package gamein2022.backend.dashboard.web.dto.result;

public class RegionResultDTO implements BaseResultDTO{
    private int teamRegionId;
    private int lastRegionId;

    public int getTeamRegionId() {
        return teamRegionId;
    }

    public void setLastRegionId(int lastRegionId) {
        this.lastRegionId = lastRegionId;
    }

    public int getLastRegionId() {
        return lastRegionId;
    }

    public void setTeamRegionId(int teamRegionId) {
        this.teamRegionId = teamRegionId;
    }
}
