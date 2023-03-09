package gamein2022.backend.dashboard.core.exception.notfound;

public class TeamNotFoundException extends NotFoundException{


    @Override
    public String getMessage() {
        return "Team not found";
    }
}
