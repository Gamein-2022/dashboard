package gamein2022.backend.dashboard.exception.notfound;

import org.springframework.http.HttpStatus;

public class TeamNotFoundException extends NotFoundException{


    @Override
    public String getMessage() {
        return "Team not found";
    }
}
