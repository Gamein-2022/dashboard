package gamein2022.backend.dashboard.data.dto.result;

public class VerifyTokenResultDto extends BaseResultDto {
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
