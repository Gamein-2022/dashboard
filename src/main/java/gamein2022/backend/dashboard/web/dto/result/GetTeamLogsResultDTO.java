package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GetTeamLogsResultDTO implements BaseResultDTO {
    private List<LogDTO> logs;
}
