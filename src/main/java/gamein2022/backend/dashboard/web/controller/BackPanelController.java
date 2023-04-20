package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.core.exception.BadRequestException;
import gamein2022.backend.dashboard.core.exception.UserNotFoundException;
import gamein2022.backend.dashboard.core.sharedkernel.entity.Team;
import gamein2022.backend.dashboard.core.sharedkernel.entity.User;
import gamein2022.backend.dashboard.infrastructure.service.auth.AuthService;
import gamein2022.backend.dashboard.web.dto.request.RegisterAndLoginRequestDTO;
import gamein2022.backend.dashboard.web.dto.result.BaseResultDTO;
import gamein2022.backend.dashboard.web.dto.result.CheckAdminResult;
import gamein2022.backend.dashboard.web.dto.result.ErrorResultDTO;
import gamein2022.backend.dashboard.web.dto.result.RegisterAndLoginResultDTO;
import gamein2022.backend.dashboard.web.iao.AuthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/panel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BackPanelController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping()
    public ResponseEntity<BaseResultDTO> checkAdmin(){
        return new ResponseEntity<>(new CheckAdminResult(true),HttpStatus.OK);
    }


}
