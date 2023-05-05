package gamein2022.backend.dashboard.web.dto.result;

import gamein2022.backend.dashboard.core.sharedkernel.enums.LogType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LogDTO {
    private LogType type;
    private Long totalCost;
    private Long count;
    private String productName;
    private LocalDateTime date;

}
