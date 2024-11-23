package udpm.hn.metu.core.admin.shape.model.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ADShapeStatusUpdateRequest {

    @Size(max = 50)
    private String shapeStatus;

}
