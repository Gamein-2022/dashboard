package gamein2022.backend.dashboard.infrastructure.service.auth;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.infrastructure.util.JwtUtils;
import gamein2022.backend.dashboard.web.dto.result.RegisterAndLoginResultDTO;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthServiceHandler implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegisterAndLoginResultDTO login(String email, String phone, String password)
            throws UserNotFoundException, BadRequestException {
        // TODO add suitable message to exceptions so front could understand what's wrong
        if (
                (email == null && phone == null)
                        || ((email != null && email.isEmpty()) && (phone != null && phone.isEmpty()))
        ) {
            throw new BadRequestException();
        }

        if (password == null || password.length() < 8) {
            throw new BadRequestException();
        }

        Optional<User> userOptional = userRepository.findByEmailOrPhone(email, phone);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userOptional.get();

        if ((new BCryptPasswordEncoder()).matches(password, user.getPassword())) {
            return new RegisterAndLoginResultDTO(JwtUtils.generateToken(user.getId()));
        }
        throw new UserNotFoundException();
    }

    @Override
    public RegisterAndLoginResultDTO register(String phone, String email, String password)
            throws BadRequestException {
        // TODO add suitable message to exceptions so front could understand what's wrong
        EmailValidator emailValidator = EmailValidator.getInstance(false);
        if (!emailValidator.isValid(email)) {
            throw new BadRequestException();
        }

        if (phone == null || phone.length() != 11) {
            throw new BadRequestException();
        }

        if (password == null || password.length() < 8) {
            throw new BadRequestException();
        }

        User user = new User();
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword((new BCryptPasswordEncoder()).encode(password));

        userRepository.save(user);

        return new RegisterAndLoginResultDTO(JwtUtils.generateToken(user.getId()));
    }

    @Override
    public AuthInfo extractAuthInfoFromToken(String token) throws InvalidTokenException {
        if (JwtUtils.isTokenExpired(token)) {
            throw new InvalidTokenException("Invalid token!");
        }
        Long id = Long.parseLong(JwtUtils.getIdFromToken(token));
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new InvalidTokenException("Invalid token!");
        }

        User user = userOptional.get();

        return new AuthInfo(user.getId(), user.getTeam() == null ? null : user.getTeam().getId());
    }
}
