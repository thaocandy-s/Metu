package udpm.hn.metu.core.admin.shape.service;

import jakarta.validation.Valid;
import udpm.hn.metu.core.admin.shape.model.request.ADShapeCreateRequest;
import udpm.hn.metu.core.admin.shape.model.request.ADShapeRequest;
import udpm.hn.metu.core.admin.shape.model.request.ADShapeStatusUpdateRequest;
import udpm.hn.metu.core.common.base.ResponseObject;
import udpm.hn.metu.entity.Shape;

public interface ADShapeService {

    ResponseObject<?> getAllShape(ADShapeRequest shapeRequest);

    ResponseObject<?> updateShape(@Valid ADShapeCreateRequest request, String id);

    ResponseObject<?> addShape(@Valid ADShapeCreateRequest request);

    ResponseObject<?> updateShapeByStatus(@Valid ADShapeStatusUpdateRequest request, String id);

    ResponseObject<?> getShapeById(String id);

}
