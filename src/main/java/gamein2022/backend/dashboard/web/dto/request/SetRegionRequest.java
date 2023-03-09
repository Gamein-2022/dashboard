package gamein2022.backend.dashboard.web.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SetRegionRequest extends BaseRequest {
    private int region;
}