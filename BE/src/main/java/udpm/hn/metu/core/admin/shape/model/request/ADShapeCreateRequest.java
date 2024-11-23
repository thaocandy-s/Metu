package udpm.hn.metu.core.admin.shape.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ADShapeCreateRequest {

    @NotEmpty
    @Size(min = 3 , max = 250)
    private String shapeName;

    @Size(min = 3 , max = 250)
    private String shapeClassName;

    @Size(max = 5000)
    private String shapeDescription;

    @Size(max = 50)
    private String shapeStatus;

    @Size(max = 500)
    private String shapeIconUrl;
}
