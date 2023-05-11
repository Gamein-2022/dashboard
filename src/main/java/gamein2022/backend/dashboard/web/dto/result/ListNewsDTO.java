package gamein2022.backend.dashboard.web.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class ListNewsDTO implements BaseResultDTO {
    private List<NewsDTO> news;
}
