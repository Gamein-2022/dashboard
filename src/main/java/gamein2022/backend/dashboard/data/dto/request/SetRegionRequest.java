package gamein2022.backend.dashboard.data.dto.request;

public class SetRegionRequest extends BaseRequest{
    private String regionId;

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionId() {
        return regionId;
    }
}
