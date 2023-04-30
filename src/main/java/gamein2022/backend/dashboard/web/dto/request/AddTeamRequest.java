package gamein2022.backend.dashboard.web.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddTeamRequest {
    private String username;
    private String password;
    private String teamName;
}
