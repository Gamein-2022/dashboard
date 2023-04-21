package gamein2022.backend.dashboard.web.controller;


import com.google.gson.Gson;
import gamein2022.backend.dashboard.infrastructure.service.panel.PanelService;
import gamein2022.backend.dashboard.web.dto.request.AddAllTeamBalanceRequestDTO;
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

    private final PanelService panelService;

    public BackPanelController(PanelService panelService) {
        this.panelService = panelService;
    }


    @GetMapping()
    public ResponseEntity<BaseResultDTO> checkAdmin() {
        try {
            return new ResponseEntity<>(new CheckAdminResult(true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("balance")
    public ResponseEntity<BaseResultDTO> addAllBalance(
            @RequestBody String requestBody
    ) {
        try {
            AddAllTeamBalanceRequestDTO dto = new Gson().fromJson(requestBody,AddAllTeamBalanceRequestDTO.class);
            panelService.addBalanceToAllTeams(dto.getAddBalance());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("start-over")
    public ResponseEntity<BaseResultDTO> restartGame(){
        try {
            panelService.restartGame();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("pause")
    public ResponseEntity<BaseResultDTO> pauseGame(){
        try {
            panelService.pauseGame();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("resume")
    public ResponseEntity<BaseResultDTO> resumeGame(){
        try {
            panelService.resumeGame();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new ErrorResultDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
