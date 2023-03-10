package gamein2022.backend.dashboard.infrastructure.service.user;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.web.dto.result.RegisterAndLoginResultDTO;
import gamein2022.backend.dashboard.web.dto.result.UserInfoResultDTO;


public interface UserService {
    UserInfoResultDTO getUserInfo();

    RegisterAndLoginResultDTO login(String email, String phone, String password) throws UserNotFoundException, BadRequestException;

    RegisterAndLoginResultDTO register(String phone, String email, String password) throws BadRequestException;
}