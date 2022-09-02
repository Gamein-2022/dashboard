package gamein2022.backend.dashboard.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "T_USER")
public class User {
    @Id
    private String userId;

    @Indexed(name = "U_EMAIL")
    private String email;

    @Indexed(name = "U_MOBILE")
    private String mobile;

    @Indexed(name = "U_PERSIAN_NAME")
    private String persianName;

    @Indexed(name = "U_ENGLISH_NAME")
    private String englishName;

    @Indexed(name = "U_TEAM_ID")
    private String teamId;

    @Indexed(name = "U_PASSWORD")
    private String password;

    @Indexed(name = "U_LAST_LOGIN")
    private Date lastLogin;

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public String getTeamId() {
        return teamId;
    }
}
