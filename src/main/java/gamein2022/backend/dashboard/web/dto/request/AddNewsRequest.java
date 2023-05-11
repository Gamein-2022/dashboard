package gamein2022.backend.dashboard.web.dto.request;

import gamein2022.backend.dashboard.core.sharedkernel.enums.NewsType;
import lombok.Getter;


@Getter
public class AddNewsRequest {
    private String title;
    private String description;
    private String image;
    private NewsType type;
}
