package gamein2022.backend.dashboard.web.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginRequestDTO {
    private String username;
    private String password;
}
