package gamein2022.backend.dashboard.web.dto.result;

public class VerifyTokenResultDTO extends BaseResultDTO {
    private String userId;

    public VerifyTokenResultDTO(boolean successful, String message, String userId) {
        super(successful, message);
        this.userId = userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}