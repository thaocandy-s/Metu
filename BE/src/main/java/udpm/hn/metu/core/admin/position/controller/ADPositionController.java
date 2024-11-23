package udpm.hn.metu.core.admin.position.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.metu.core.admin.position.model.request.ADPositionCreateRequest;
import udpm.hn.metu.core.admin.position.model.request.ADPositionRequest;
import udpm.hn.metu.core.admin.position.model.request.ADPositionStatusUpdateRequest;
import udpm.hn.metu.core.admin.position.service.ADPositionService;
import udpm.hn.metu.infrastructure.constant.MappingConstant;
import udpm.hn.metu.utils.Helper;

@RestController
@RequestMapping(MappingConstant.API_ADMIN_POSITION)
@RequiredArgsConstructor
public class ADPositionController {

    @Autowired
    private final ADPositionService positionService;

    @GetMapping("")
    public ResponseEntity<?> getAllPosition(ADPositionRequest request) {
        return Helper.createResponseEntity(positionService.getAllPosition(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPositionById(@PathVariable("id") String id) {
        return Helper.createResponseEntity(positionService.getPositionById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> addPosition(@Valid @RequestBody ADPositionCreateRequest request) {
        return Helper.createResponseEntity(positionService.addPosition(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePosition(@Valid @RequestBody ADPositionCreateRequest request, @PathVariable("id") String id) {
        return Helper.createResponseEntity(positionService.updatePosition(request,id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updatePositionByStatus(@Valid @RequestBody ADPositionStatusUpdateRequest request, @PathVariable("id") String id) {
        return Helper.createResponseEntity(positionService.updatePositionByStatus(request, id));
    }

}
