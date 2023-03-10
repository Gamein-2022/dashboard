package gamein2022.backend.dashboard.infrastructure.service.user;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.web.dto.result.RegisterAndLoginResultDTO;
import gamein2022.backend.dashboard.web.dto.result.UserInfoResultDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceHandler implements UserService {

    private final UserRepository userRepository;

    public UserServiceHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserInfoResultDTO getUserInfo() {
        return null;
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
            String token =
                    Jwts.builder()
                            .setSubject(user.getId().toString())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date((new Date()).getTime() + 86400000))
                            .signWith(
                                    SignatureAlgorithm.HS512,
                                    DatatypeConverter.parseBase64Binary("SECRET_KEY")
                            ).compact();
            return new RegisterAndLoginResultDTO(token);
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

        String token = Jwts.builder().setSubject(user.getId().toString()).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + 86400000)).signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary("SECRET_KEY")).compact();

        return new RegisterAndLoginResultDTO(token);
    }
}
