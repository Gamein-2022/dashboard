package gamein2022.backend.dashboard.data.dto.result;

public class BaseResultDto {
    protected boolean successful;

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
