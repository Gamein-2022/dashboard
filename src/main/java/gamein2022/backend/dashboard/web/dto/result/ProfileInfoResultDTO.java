package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ProfileInfoResultDTO implements BaseResultDTO {
    private String englishName;
    private String persianName;
}
