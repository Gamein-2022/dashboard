package gamein2022.backend.dashboard.data.dto.result;

public class BaseResultDto {
    protected boolean successful;
    protected String message;

    public BaseResultDto(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
