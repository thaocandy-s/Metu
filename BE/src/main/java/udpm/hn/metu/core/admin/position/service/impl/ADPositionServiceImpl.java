package udpm.hn.metu.core.admin.position.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import udpm.hn.metu.core.admin.position.model.request.ADPositionCreateRequest;
import udpm.hn.metu.core.admin.position.model.request.ADPositionRequest;
import udpm.hn.metu.core.admin.position.model.request.ADPositionStatusUpdateRequest;
import udpm.hn.metu.core.admin.position.repository.ADPositionExtendRepository;
import udpm.hn.metu.core.admin.position.service.ADPositionService;
import udpm.hn.metu.core.common.base.PageableObject;
import udpm.hn.metu.core.common.base.ResponseObject;
import udpm.hn.metu.entity.Position;
import udpm.hn.metu.entity.Shape;
import udpm.hn.metu.infrastructure.constant.enums.Status;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Validated
public class ADPositionServiceImpl implements ADPositionService {

    private final ADPositionExtendRepository positionExtendRepository;

    private static int currentId = 0;

    @Override
    public ResponseObject<?> getAllPosition(ADPositionRequest positionRequest) {
        Pageable pageable = PageRequest.of(positionRequest.getPage() - 1, positionRequest.getSize());
        return new ResponseObject<>(
                PageableObject.of(positionExtendRepository.getAllPosition(pageable, positionRequest)),
                HttpStatus.OK,
                "Lấy vị trí thành công!"
        );
    }

    @Override
    public ResponseObject<?> updatePosition(ADPositionCreateRequest request, String id) {
        Optional<Position> position = positionExtendRepository.findById(id)
                .map(position1 -> {
                    position1.setName(request.getPositionName());
                    position1.setClassName(request.getPositionClassName());
                    position1.setDescription(request.getPositionDescription());
                    position1.setIconUrl(request.getPositionIconUrl());
                    return positionExtendRepository.save(position1);
                });
        return position
                .map(position1 -> new ResponseObject<>(null, HttpStatus.OK, "Cập nhật vị trí thành công"))
                .orElseGet(() -> new ResponseObject<>(null,HttpStatus.NOT_FOUND,"Vị trí không tồn tại"));
    }

    @Override
    public ResponseObject<?> addPosition(ADPositionCreateRequest request) {
        Random random = new Random();
        int number = random.nextInt(10000);
        String code = String.format("TL%04d", number);
        Position position = new Position();
        position.setId(String.valueOf(++currentId));
        position.setCode(code);
        position.setName(request.getPositionName());
        position.setClassName(request.getPositionClassName());
        position.setIconUrl(request.getPositionIconUrl());
        position.setStatus(Status.ACTIVE);
        position.setDescription(request.getPositionDescription());

        Position addposition = positionExtendRepository.save(position);
        return new ResponseObject<>(null,HttpStatus.CREATED, "Tạo vị trí thành công");
    }

    @Override
    public ResponseObject<?> updatePositionByStatus(ADPositionStatusUpdateRequest request, String id) {
        Optional<Position> position = positionExtendRepository.findById(id)
                .map(existingPosition -> {
                    if (!request.getPositionStatus().equals("0") && !request.getPositionStatus().equals("1")) {
                        throw new IllegalArgumentException("Trạng thái hình ảnh không hợp lệ");
                    }
                    existingPosition.setStatus(request.getPositionStatus().equals("0") ? Status.ACTIVE : Status.INACTIVE);
                    return positionExtendRepository.save(existingPosition);
                });

        return position
                .map(updatePosition -> new ResponseObject<>(null, HttpStatus.OK, "Cập nhật vị trí thành công"))
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, "vị trí không tồn tại"));
    }

    @Override
    public ResponseObject<?> getPositionById(String id) {
        return positionExtendRepository.getAllPositionById(id)
                .map(subject -> new ResponseObject<>(subject, HttpStatus.OK, "Lấy thông tin vị tri thành công"))
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, id + "Không tồn tại"));
    }
}
