package gamein2022.backend.dashboard.web.controller;


import gamein2022.backend.dashboard.web.dto.result.BaseResultDTO;
import gamein2022.backend.dashboard.web.dto.result.CheckAdminResult;
import gamein2022.backend.dashboard.web.dto.result.ErrorResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/panel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BackPanelController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping()
    public ResponseEntity<BaseResultDTO> checkAdmin(){
        try {
            return new ResponseEntity<>(new CheckAdminResult(true),HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
