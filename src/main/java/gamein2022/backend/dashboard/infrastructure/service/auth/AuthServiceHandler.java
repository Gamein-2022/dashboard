package gamein2022.backend.dashboard.infrastructure.service.auth;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.InvalidTokenException;
import gamein2022.backend.dashboard.core.exception.UserAlreadyExist;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Time;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.repository.TimeRepository;
import gamein2022.backend.dashboard.infrastructure.repository.UserRepository;
import gamein2022.backend.dashboard.infrastructure.util.JwtUtils;
import gamein2022.backend.dashboard.web.dto.result.RegisterAndLoginResultDTO;
import gamein2022.backend.dashboard.web.dto.result.TimeResultDTO;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;


@Service
public class AuthServiceHandler implements AuthService {

    private final UserRepository userRepository;
    private final TimeRepository timeRepository;



    public AuthServiceHandler(UserRepository userRepository, TimeRepository timeRepository) {
        this.userRepository = userRepository;
        this.timeRepository = timeRepository;
    }

    @Override
    public RegisterAndLoginResultDTO login(String email, String phone, String password)
            throws UserNotFoundException, BadRequestException {
        if (
                (email == null && phone == null)
                        || ((email != null && email.isEmpty()) && (phone != null && phone.isEmpty()))
        ) {
            throw new BadRequestException("اطلاعات وارد شده صحیح نمی باشد");
        }

        if (password == null || password.length() < 8) {
            throw new BadRequestException("اطلاعات وارد شده صحیح نمی باشد");
        }

        Optional<User> userOptional = userRepository.findByEmailOrPhone(email, phone);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("اطلاعات وارد شده صحیح نمی باشد");
        }

        User user = userOptional.get();

        if ((new BCryptPasswordEncoder()).matches(password, user.getPassword())) {
            return new RegisterAndLoginResultDTO(JwtUtils.generateToken(user.getId()));
        }
        throw new UserNotFoundException();
    }



    @Override
    public RegisterAndLoginResultDTO register(String phone, String email, String password)
            throws BadRequestException, UserAlreadyExist {

        EmailValidator emailValidator = EmailValidator.getInstance(false);
        if (!emailValidator.isValid(email)) {
            throw new BadRequestException("اطلاعات وارد شده صحیح نمی باشد");
        }

        if (phone == null || phone.length() != 11) {
            throw new BadRequestException("اطلاعات وارد شده صحیح نمی باشد");
        }

        if (password == null || password.length() < 8) {
            throw new BadRequestException("اطلاعات وارد شده صحیح نمی باشد");
        }

        Optional<User> userOptional = userRepository.findByEmailOrPhone(email, phone);

        if (userOptional.isPresent()) {
            throw new UserAlreadyExist("کاربری با این شماره و ایمیل وجود دارد");
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
            throw new InvalidTokenException("توکن ارسالی معتبر نمی‌باشد");
        }
        Long id = Long.parseLong(JwtUtils.getIdFromToken(token));
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new InvalidTokenException("توکن ارسالی معتبر نمی‌باشد");
        }


        User user = userOptional.get();
        Time time = timeRepository.findById(1L).get();
        return new AuthInfo(
                user.getId(),
                user.getTeam() == null ? null : user.getTeam().getId(),
                user.getTeam() == null ? null : user.getTeam().getName(),
                user.getTeam() == null ? null : user.getTeam().getBalance(),
                time.getIsGamePaused());
    }

    @Override
    public TimeResultDTO getTime() {
        Time time = timeRepository.findById(1L).get();
        LocalDateTime beginDate = time.getBeginTime();
        Long stoppedSeconds = time.getStoppedTimeSeconds();
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        long durationSeconds = Duration.between(beginDate, now).toSeconds() - stoppedSeconds;



        long daySeconds = 8L;
        long monthSeconds = 30L * daySeconds;
        long yearSeconds = 12 * monthSeconds;
        long daysFromBeginning = durationSeconds / daySeconds;
        long monthFromBeginning = durationSeconds / monthSeconds;
        long yearFromBeginning = durationSeconds / yearSeconds;

        byte era = 0;
        if (daysFromBeginning >= 7425)
            era = 4;
        else if (daysFromBeginning >= 4688)
            era = 3;
        else if (daysFromBeginning >= 2738)
            era = 2;
        else if (daysFromBeginning >= 1163) {
            era = 1;
        }

        long year = 2002 + (yearFromBeginning) + 1;

        long month = ((8 + monthFromBeginning) % 12) + 1;

        long day = ((14 + daysFromBeginning) % 30) + 1;


        TimeResultDTO timeResultDTO = new TimeResultDTO();
        timeResultDTO.setSecondOfDate(durationSeconds);
        timeResultDTO.setDay(day);
        timeResultDTO.setMonth(month);
        timeResultDTO.setYear(year);
        timeResultDTO.setEra(era);
        timeResultDTO.setIsGamePaused(time.getIsGamePaused());
        return timeResultDTO;
    }

    @Override
    public User getUserById(Long userId) throws BadRequestException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new BadRequestException("User does not exist!");
        }
        return userOptional.get();
    }
}
