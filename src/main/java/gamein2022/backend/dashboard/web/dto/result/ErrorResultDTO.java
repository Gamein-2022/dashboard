package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public class ErrorResultDTO implements BaseResultDTO {
    private String message;
    private HttpStatus status;
}
