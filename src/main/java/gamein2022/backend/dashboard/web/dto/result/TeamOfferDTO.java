package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class TeamOfferDTO implements BaseResultDTO {
    private Long id;
    private String teamName;
    private String Username;
}
