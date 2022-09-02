package gamein2022.backend.dashboard.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "T_TEAM")
public class Team {
    @Id
    private String teamId;

    @Indexed(name = "T_BRAND")
    private String teamBrand;

    @Indexed(name = "T_BUDGET")
    private Long teamBudget;

    @Indexed(name = "T_NAME")
    private String teamName;

    @Indexed(name = "T_FACTORY_ID")
    private String teamFactoryId;

    @Indexed(name = "T_USER_IDS")
    private List<String> teamUserIds;

    @Indexed(name = "T_REGION_ID")
    private String teamRegionId;

    public List<String> getTeamUserIds() {
        return teamUserIds;
    }

    public Long getTeamBudget() {
        return teamBudget;
    }

    public String getTeamBrand() {
        return teamBrand;
    }

    public String getTeamFactoryId() {
        return teamFactoryId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamRegionId() {
        return teamRegionId;
    }

    public void setTeamBrand(String teamBrand) {
        this.teamBrand = teamBrand;
    }

    public void setTeamBudget(Long teamBudget) {
        this.teamBudget = teamBudget;
    }

    public void setTeamFactoryId(String teamFactoryId) {
        this.teamFactoryId = teamFactoryId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamRegionId(String teamRegionId) {
        this.teamRegionId = teamRegionId;
    }

    public void setTeamUserIds(List<String> teamUserIds) {
        this.teamUserIds = teamUserIds;
    }
}
