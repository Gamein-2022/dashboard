package gamein2022.backend.dashboard.web.dto.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetTop100Result implements BaseResultDTO{
    private List<WealthDto> topTeams;
}
