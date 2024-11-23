package udpm.hn.metu.core.admin.position.model.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ADPositionStatusUpdateRequest {

    @Size(max = 50)
    private String positionStatus;

}
