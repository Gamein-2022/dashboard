package gamein2022.backend.dashboard.infrastructure.service.profile;

import gamein2022.backend.dashboard.core.sharedkernel.enums.NewsType;
import gamein2022.backend.dashboard.web.dto.result.NewsNotifsDTO;


public interface ProfileService {
    NewsNotifsDTO getNews(NewsType type);
}
