package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class NewsNotifsDTO implements BaseResultDTO {
    private List<NewsDTO> news;
    private List<NewsDTO> notifs;
}
