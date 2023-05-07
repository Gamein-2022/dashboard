package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserDTO implements BaseResultDTO {
    private Long id;
    private String name;
}
