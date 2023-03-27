package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;


@AllArgsConstructor
@Getter
public class TeamResearchDTO {
    private ResearchSubjectDTO subject;
    private Date beginTime;
    private Date endTime;
}
