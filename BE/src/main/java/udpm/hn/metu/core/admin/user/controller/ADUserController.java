package udpm.hn.metu.core.admin.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.metu.core.common.base.ResponseObject;
import udpm.hn.metu.infrastructure.constant.MappingConstant;
import udpm.hn.metu.core.admin.user.model.request.ADSearchUserRepuest;
import udpm.hn.metu.core.admin.user.model.request.ADUserRequest;
import udpm.hn.metu.core.admin.user.service.ADUserService;
import udpm.hn.metu.utils.Helper;

@RestController
@RequestMapping(MappingConstant.API_ADMIN_USER)
@RequiredArgsConstructor
public class ADUserController {

    private final ADUserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser(final ADSearchUserRepuest searchUserRepuest){
        return Helper.createResponseEntity(userService.getAllUser(searchUserRepuest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        return Helper.createResponseEntity(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody ADUserRequest userRequest, @PathVariable("id") String id){
        return Helper.createResponseEntity(userService.updateUser(id, userRequest));
    }

    @PutMapping("/{id}/change-status")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id) {
        return Helper.createResponseEntity(userService.changeUserStatus(id));
    }

}
