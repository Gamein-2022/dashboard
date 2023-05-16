package gamein2022.backend.dashboard.infrastructure.service.profile;

import gamein2022.backend.dashboard.core.sharedkernel.entity.News;
import gamein2022.backend.dashboard.core.sharedkernel.enums.NewsType;
import gamein2022.backend.dashboard.infrastructure.repository.NewsRepository;
import gamein2022.backend.dashboard.web.dto.result.NewsNotifsDTO;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class ProfileServiceHandler implements ProfileService {
    private final NewsRepository newsRepository;



    public ProfileServiceHandler(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public NewsNotifsDTO getNews(NewsType type) {
        return new NewsNotifsDTO(
                newsRepository.findAllByType(type).stream().map(News::toDTO).collect(Collectors.toList())
        );
    }
}
