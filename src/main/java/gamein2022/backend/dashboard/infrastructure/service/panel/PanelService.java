package gamein2022.backend.dashboard.infrastructure.service.panel;

import gamein2022.backend.dashboard.web.dto.result.GetTop100Result;
import gamein2022.backend.dashboard.web.dto.result.WealthDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PanelService {
    void addBalanceToAllTeams(Long addBalance);

    void restartGame();

    void pauseGame();

    void resumeGame();

    GetTop100Result getTop100();

}
