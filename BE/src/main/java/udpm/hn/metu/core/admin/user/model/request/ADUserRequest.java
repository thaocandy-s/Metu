package udpm.hn.metu.core.admin.user.model.request;

import lombok.Getter;
import lombok.Setter;
import udpm.hn.metu.core.common.base.PageableRequest;

@Getter
@Setter

public class ADUserRequest extends PageableRequest {

    String name;

    String email;

    String code;

    String address;

    String avatarImage;


}
