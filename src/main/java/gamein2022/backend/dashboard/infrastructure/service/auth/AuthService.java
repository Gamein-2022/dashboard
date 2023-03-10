package gamein2022.backend.dashboard.infrastructure.service.auth;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.web.dto.result.RegisterAndLoginResultDTO;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import io.jsonwebtoken.MalformedJwtException;


public interface AuthService {
    RegisterAndLoginResultDTO login(String email, String phone, String password) throws UserNotFoundException, BadRequestException;

    RegisterAndLoginResultDTO register(String phone, String email, String password) throws BadRequestException;

    AuthInfo extractAuthInfoFromToken(String token) throws InvalidTokenException, MalformedJwtException;
}