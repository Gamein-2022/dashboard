package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class NewsDTO {
    private String title;
    private String description;
    private String image;
    private String date;
}
