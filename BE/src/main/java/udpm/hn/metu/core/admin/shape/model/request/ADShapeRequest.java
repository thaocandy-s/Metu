package udpm.hn.metu.core.admin.shape.model.request;

import lombok.Getter;
import lombok.Setter;
import udpm.hn.metu.core.common.base.PageableRequest;

@Getter
@Setter
public class ADShapeRequest extends PageableRequest {

    private String shapeCode;

    private String shapeName;

    private Integer shapeStatus;

}
