package gamein2022.backend.dashboard.web.controller;

import gamein2022.backend.dashboard.web.dto.result.BaseResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HealthCheck {


    @GetMapping()
    public ResponseEntity<String> checkHealth(){
        return new ResponseEntity<>("Up", HttpStatus.OK);
    }
}
