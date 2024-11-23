package udpm.hn.metu.core.admin.position.service;

import jakarta.validation.Valid;
import udpm.hn.metu.core.admin.position.model.request.ADPositionCreateRequest;
import udpm.hn.metu.core.admin.position.model.request.ADPositionRequest;
import udpm.hn.metu.core.admin.position.model.request.ADPositionStatusUpdateRequest;
import udpm.hn.metu.core.common.base.ResponseObject;

public interface ADPositionService {

    ResponseObject<?> getAllPosition(ADPositionRequest positionRequest);

    ResponseObject<?> updatePosition(@Valid ADPositionCreateRequest request, String id);

    ResponseObject<?> addPosition(@Valid ADPositionCreateRequest request);

    ResponseObject<?> updatePositionByStatus(@Valid ADPositionStatusUpdateRequest request, String id);

    ResponseObject<?> getPositionById(String id);
}
