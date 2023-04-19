package gamein2022.backend.dashboard.web.dto.result;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TimeResultDTO implements BaseResultDTO {
    private Long day;
    private Long month;
    private Long year;
    private Byte era;
    private Long secondOfDate;

}
