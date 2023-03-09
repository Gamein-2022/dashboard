package gamein2022.backend.dashboard.infrastructure.service.user;

import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.web.dto.result.LoginResultDTO;
import gamein2022.backend.dashboard.web.dto.result.RegisterResultDTO;
import gamein2022.backend.dashboard.web.dto.result.UserInfoResultDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    public LoginResultDTO login(String username, String password) throws Exception {
        Optional<User> userOptional = userRepository.findByPhone(username);

        if (userOptional.isEmpty()) {
            throw new Exception("failed");
        }

        User user = userOptional.get();

        if ((new BCryptPasswordEncoder()).matches(password, user.getPassword())) {
//        if (user.getPassword().equals(password)) {
            String token =
                    Jwts.builder()
                            .setSubject(user.getId().toString())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date((new Date()).getTime() + 86400000))
                            .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary("SECRET_KEY"))
                            .compact();
            return new LoginResultDTO(token);
        }
        throw new Exception("failed");
    }

    @Override
    public RegisterResultDTO register(String phone, String email, String password) throws Exception {
        // TODO validate
        User user = new User();
        user.setPhone(phone);
        user.setEmail(email);
//        user.setPassword(password);
        user.setPassword((new BCryptPasswordEncoder()).encode(password));

        userRepository.save(user);

        String token =
                Jwts.builder()
                        .setSubject(user.getId().toString())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date((new Date()).getTime() + 86400000))
                        .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary("SECRET_KEY"))
                        .compact();

        return new RegisterResultDTO(token);
    }
}
