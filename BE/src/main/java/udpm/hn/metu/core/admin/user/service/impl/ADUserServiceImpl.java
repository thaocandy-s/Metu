package udpm.hn.metu.core.admin.user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.metu.core.common.base.PageableObject;
import udpm.hn.metu.core.common.base.ResponseObject;
import udpm.hn.metu.entity.User;
import udpm.hn.metu.core.admin.user.model.request.ADSearchUserRepuest;
import udpm.hn.metu.core.admin.user.model.request.ADUserRequest;
import udpm.hn.metu.core.admin.user.repository.ADUserExtendRepository;
import udpm.hn.metu.core.admin.user.service.ADUserService;
import udpm.hn.metu.infrastructure.constant.enums.Status;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ADUserServiceImpl implements ADUserService {

    private final ADUserExtendRepository userRepository;

    @Override
    public ResponseObject<?> getAllUser(ADSearchUserRepuest request) {
         Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        return ResponseObject.successForward(PageableObject.of(
                userRepository.getAllUser(pageable, request)),
                "Lấy danh sách thể loại doanh nghiệp thành công!");
    }

    @Override
    public ResponseObject<?> getUserById(String id) {
        return userRepository.getUserById(id)
                .map(subject -> new ResponseObject<>(subject, HttpStatus.OK, "Lấy thông tin người dùng thành công"))
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, id + " không tồn tại"));
    }

    @Override
    public ResponseObject<?>  updateUser(String id, ADUserRequest userRequest) {

        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            User  u = user.get();
            if(userRequest.getName() != null && !userRequest.getName().isEmpty()){
                u.setFullName(userRequest.getName());
            }
            if(userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()){
                u.setEmail(userRequest.getEmail());
            }
            if(userRequest.getAddress() != null && !userRequest.getAddress().isEmpty()){
                u.setAddress(userRequest.getAddress());
            }
            if(userRequest.getAvatarImage() != null && !userRequest.getAvatarImage().isEmpty()){
                u.setAvatarImage(userRequest.getAvatarImage());
            }

            return ResponseObject.successForward( userRepository.save(u), "update thành công");

        }else {

            return ResponseObject.errorForward("update thất bại", HttpStatus.NOT_FOUND);

        }
    }


    @Override
    public ResponseObject<?> changeUserStatus(String facilityId) {
        Optional<User> userOptional = userRepository.findById(facilityId);

        userOptional.map(facility -> {
            facility.setFullName(facility.getFullName());
            facility.setStatus(facility.getStatus() != Status.ACTIVE ? Status.ACTIVE : Status.INACTIVE);
            return ResponseObject.successForward( userRepository.save(facility), "thay đổi trạng thái thành công");
        });

        return userOptional
                .map(subject -> ResponseObject.successForward(HttpStatus.OK, "Đổi trạng thái người dùng thành công"))
                .orElseGet(() ->  ResponseObject.successForward(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng"));
    }

}
