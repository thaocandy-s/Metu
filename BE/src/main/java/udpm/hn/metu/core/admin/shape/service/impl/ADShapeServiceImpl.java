package udpm.hn.metu.core.admin.shape.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import udpm.hn.metu.core.admin.shape.model.request.ADShapeCreateRequest;
import udpm.hn.metu.core.admin.shape.model.request.ADShapeRequest;
import udpm.hn.metu.core.admin.shape.model.request.ADShapeStatusUpdateRequest;
import udpm.hn.metu.core.admin.shape.repository.ADShapeExtendRepository;
import udpm.hn.metu.core.admin.shape.service.ADShapeService;
import udpm.hn.metu.core.common.base.PageableObject;
import udpm.hn.metu.core.common.base.ResponseObject;
import udpm.hn.metu.entity.Shape;
import udpm.hn.metu.infrastructure.constant.enums.Status;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Validated
public class ADShapeServiceImpl implements ADShapeService {

    private final ADShapeExtendRepository shapeExtendRepository;

    private static int currentId = 0;

    @Override
    public ResponseObject<?> getAllShape(ADShapeRequest shapeRequest) {
        Pageable pageable = PageRequest.of(shapeRequest.getPage() - 1, shapeRequest.getSize());
        return new ResponseObject<>(
                PageableObject.of(shapeExtendRepository.getAllShapeByStatus(pageable, shapeRequest)),
                HttpStatus.OK,
                "Lấy Hình Dạng thành công!"
        );
    }

    @Override
    public ResponseObject<?> updateShape(ADShapeCreateRequest request, String id) {
        Optional<Shape> shape = shapeExtendRepository.findById(id)
                .map(shape1 -> {
                    shape1.setName(request.getShapeName());
                    shape1.setClassName(request.getShapeClassName());
                    shape1.setDescription(request.getShapeDescription());
                    shape1.setIconUrl(request.getShapeIconUrl());
                    return shapeExtendRepository.save(shape1);
                });
        return shape
                .map(shape1 -> new ResponseObject<>(null, HttpStatus.OK, "Cập nhật Hình Dạng thành công"))
                .orElseGet(() -> new ResponseObject<>(null,HttpStatus.NOT_FOUND,"Hình dạng không tồn tại"));
    }


    @Override
    public ResponseObject<?> addShape(@Valid ADShapeCreateRequest request) {
        Random random = new Random();
        int number = random.nextInt(10000);
        String code = String.format("TL%04d", number);
        Shape shape = new Shape();
        shape.setId(String.valueOf(++currentId));
        shape.setCode(code);
        shape.setName(request.getShapeName());
        shape.setClassName(request.getShapeClassName());
        shape.setIconUrl(request.getShapeIconUrl());
        shape.setStatus(Status.ACTIVE);
        shape.setDescription(request.getShapeDescription());
        Shape addShape = shapeExtendRepository.save(shape);
        return new ResponseObject<>(null,HttpStatus.CREATED, "Tạo hình dạng thành công");
    }

    @Override
    public ResponseObject<?> updateShapeByStatus(ADShapeStatusUpdateRequest request, String id) {
        Optional<Shape> shape = shapeExtendRepository.findById(id)
                .map(existingShape -> {
                    if (!request.getShapeStatus().equals("0") && !request.getShapeStatus().equals("1")) {
                        throw new IllegalArgumentException("Trạng thái hình ảnh không hợp lệ");
                    }
                    existingShape.setStatus(request.getShapeStatus().equals("0") ? Status.ACTIVE : Status.INACTIVE);
                    return shapeExtendRepository.save(existingShape);
                });

        return shape
                .map(updatedShape -> new ResponseObject<>(null, HttpStatus.OK, "Cập nhật hình dạng thành công"))
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Hình dạng không tồn tại"));
    }


    @Override
    public ResponseObject<?> getShapeById(String id) {
        return shapeExtendRepository.getAllShapeById(id)
                .map(subject -> new ResponseObject<>(subject, HttpStatus.OK, "Lấy thông tin hình dạng thành công"))
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, id + "Không tồn tại"));
    }

}
