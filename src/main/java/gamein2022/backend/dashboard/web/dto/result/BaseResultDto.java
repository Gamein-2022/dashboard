package gamein2022.backend.dashboard.web.dto.result;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BaseResultDto {
    protected boolean successful;
    protected String message;

    public BaseResultDto(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }
}