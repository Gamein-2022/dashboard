package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ResearchSubjectDTO {
    private String name;
    private int availableDay;
    private int basePrice;
    private int baseDuration;
}
