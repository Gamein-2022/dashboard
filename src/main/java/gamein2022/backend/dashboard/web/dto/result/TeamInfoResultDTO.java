package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@Getter
public class TeamInfoResultDTO implements BaseResultDTO {
    private String name;
    private List<UserDTO> users;
    private Boolean isOwner;
}
