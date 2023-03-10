package gamein2022.backend.dashboard.web.dto.request;

import lombok.Getter;


@Getter
public class RegisterAndLoginRequestDTO {
    private String phone;
    private String email;
    private String password;
}
