package gamein2022.backend.dashboard.infrastructure.service.panel;

public interface PanelService {
    void addBalanceToAllTeams(Long addBalance);

    void restartGame();

    void pauseGame();

    void resumeGame();

}
