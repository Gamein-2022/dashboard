package gamein2022.backend.dashboard.infrastructure.service.profile;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.News;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.core.sharedkernel.enums.NewsType;
import gamein2022.backend.dashboard.infrastructure.repository.NewsRepository;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.web.dto.request.ProfileInfoRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.ListNewsDTO;
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
    public ProfileInfoResultDTO getProfileInfo(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userOptional.get();

        return new ProfileInfoResultDTO(user.getEnglishName(), user.getPersianName());
    }

    @Override
    public ProfileInfoResultDTO setProfileInfo(Long id, ProfileInfoRequestDTO info) throws UserNotFoundException, BadRequestException {
        if (info.getEnglishName() == null && info.getPersianName() == null) {
            throw new BadRequestException();
        }

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userOptional.get();

        if (info.getEnglishName() != null) {
            user.setEnglishName(info.getEnglishName());
        }
        if (info.getPersianName() != null) {
            user.setPersianName(info.getPersianName());
        }

        userRepository.save(user);

        return new ProfileInfoResultDTO(user.getEnglishName(), user.getPersianName());
    }

    @Override
    public ListNewsDTO getNews() {
        return new ListNewsDTO(
                newsRepository.findAllByType(NewsType.NEWS).stream().map(News::toDTO).collect(Collectors.toList())
        );
    }

    @Override
    public ListNewsDTO getNotifs() {
        return new ListNewsDTO(
                newsRepository.findAllByType(NewsType.NOTIFICATION).stream().map(News::toDTO).collect(Collectors.toList())
        );
    }
}
