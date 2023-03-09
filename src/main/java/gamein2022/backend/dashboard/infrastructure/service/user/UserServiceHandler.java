package gamein2022.backend.dashboard.infrastructure.service.user;

import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.web.dto.result.LoginResultDto;
import gamein2022.backend.dashboard.web.dto.result.UserInfoResultDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    public UserInfoResultDto getUserInfo() {
        return null;
    }

    @Override
    public LoginResultDto login(String username, String password) throws Exception {
        Optional<User> userOptional = userRepository.findByPhone(username);

        if (userOptional.isEmpty()) {
            throw new Exception("failed");
        }

        User user = userOptional.get();

        if (user.getPassword().equals(password)) {
            String token =
                    Jwts.builder()
                            .setSubject(user.getId().toString())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date((new Date()).getTime() + 86400000))
                            .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary("SECRET_KEY"))
                            .compact();
            return new LoginResultDto(token);
        }
        throw new Exception("failed");
    }
}
