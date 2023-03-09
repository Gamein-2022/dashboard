package gamein2022.backend.dashboard.infrastructure.service.user;

import gamein2022.backend.dashboard.web.dto.result.LoginResultDTO;
import gamein2022.backend.dashboard.web.dto.result.RegisterResultDTO;
import gamein2022.backend.dashboard.web.dto.result.UserInfoResultDTO;


public interface UserService {
    UserInfoResultDTO getUserInfo();

    LoginResultDTO login(String username, String password) throws Exception;

    RegisterResultDTO register(String phone, String email, String password) throws Exception;
}