package gamein2022.backend.dashboard.infrastructure.service.auth;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.UserAlreadyExist;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.web.dto.result.RegisterAndLoginResultDTO;
import gamein2022.backend.dashboard.web.dto.result.TimeResultDTO;
import gamein2022.backend.dashboard.web.iao.AuthInfo;


public interface AuthService {
    RegisterAndLoginResultDTO login(String email, String phone, String password) throws UserNotFoundException, BadRequestException;

    RegisterAndLoginResultDTO register(String phone, String email, String password) throws BadRequestException, UserAlreadyExist;

    AuthInfo extractAuthInfoFromToken(String token) throws InvalidTokenException;

    User getUserById(Long userId) throws BadRequestException;
    void forgotPassword(String email) throws UserNotFoundException;
    void resetPassword(String code, String password) throws BadRequestException;


    TimeResultDTO getTime();
}