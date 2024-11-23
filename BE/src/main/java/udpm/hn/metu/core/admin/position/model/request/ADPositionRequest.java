package udpm.hn.metu.core.admin.position.model.request;

import lombok.Getter;
import lombok.Setter;
import udpm.hn.metu.core.common.base.PageableRequest;

@Getter
@Setter
public class ADPositionRequest extends PageableRequest {

    private String positionCode;

    private String positionName;

    private Integer positionStatus;

}
