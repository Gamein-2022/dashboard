package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetTeamWealthResultDTO implements BaseResultDTO{
    private Long wealth;
}
