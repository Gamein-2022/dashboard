package gamein2022.backend.dashboard.web.iao;

import gamein2022.backend.dashboard.web.dto.result.BaseResultDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class AuthInfo implements BaseResultDTO {
    private Long userId;
    private Long teamId;
    private String teamName;
    private Long balance;
    private Boolean isGamePaused;
    private Integer region;
}
