package udpm.hn.metu.core.admin.user.service;

import udpm.hn.metu.core.admin.user.model.request.ADSearchUserRepuest;
import udpm.hn.metu.core.admin.user.model.request.ADUserRequest;
import udpm.hn.metu.core.common.base.ResponseObject;
import udpm.hn.metu.entity.User;

public interface ADUserService {

    ResponseObject<?> getAllUser(ADSearchUserRepuest request);

    ResponseObject<?> getUserById(String id);

    ResponseObject<?>  updateUser(String id, ADUserRequest userRequest);

    ResponseObject<?> changeUserStatus(String userId);
}
