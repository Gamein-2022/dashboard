package gamein2022.backend.dashboard.web.dto.request;

import lombok.Getter;


@Getter
public class ResetPasswordRequestDTO {
    private String code;
    private String password;
}
