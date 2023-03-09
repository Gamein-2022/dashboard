package gamein2022.backend.dashboard.infrastructure.service.user;

import gamein2022.backend.dashboard.web.dto.result.UserInfoResultDto;


public interface UserService {
    UserInfoResultDto getUserInfo();
}