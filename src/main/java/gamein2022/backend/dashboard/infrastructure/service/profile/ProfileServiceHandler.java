package gamein2022.backend.dashboard.infrastructure.service.profile;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.News;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.core.sharedkernel.enums.NewsType;
import gamein2022.backend.dashboard.infrastructure.repository.NewsRepository;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.web.dto.request.ProfileInfoRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.NewsNotifsDTO;
import gamein2022.backend.dashboard.web.dto.result.ProfileInfoResultDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProfileServiceHandler implements ProfileService {
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;



    public ProfileServiceHandler(UserRepository userRepository, NewsRepository newsRepository) {
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public NewsNotifsDTO getNews(NewsType type) {
        return new NewsNotifsDTO(
                newsRepository.findAllByType(type).stream().map(News::toDTO).collect(Collectors.toList())
        );
    }
}
