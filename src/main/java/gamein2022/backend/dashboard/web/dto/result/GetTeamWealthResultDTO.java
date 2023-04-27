package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetTeamWealthResultDTO implements BaseResultDTO{
    private Long teamWealth;
    private Long brand;
    private Long lastTopWealth;
    private Long rank;
    private List<WealthDto> upper;
    private List<WealthDto> lower;

}
