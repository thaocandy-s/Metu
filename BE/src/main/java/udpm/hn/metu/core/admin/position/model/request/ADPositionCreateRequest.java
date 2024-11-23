package udpm.hn.metu.core.admin.position.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ADPositionCreateRequest {

    @NotEmpty
    @Size(min = 3 , max = 250)
    private String positionName;

    @Size(min = 3 , max = 250)
    private String positionClassName;

    @Size(max = 5000)
    private String positionDescription;

    @Size(max = 50)
    private String positionStatus;

    @Size(max = 500)
    private String positionIconUrl;

}
