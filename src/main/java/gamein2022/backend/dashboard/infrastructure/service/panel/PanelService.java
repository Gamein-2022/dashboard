package gamein2022.backend.dashboard.infrastructure.service.panel;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.enums.NewsType;
import gamein2022.backend.dashboard.web.dto.request.AddNewsRequest;
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

    void addTeam(String username, String password, String teamName) throws UserNotFoundException, BadRequestException, InvalidTokenException;

    void addNews(String title, String desc, String image, NewsType type, String date);
}
