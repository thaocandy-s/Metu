package udpm.hn.metu.core.admin.shape.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udpm.hn.metu.core.admin.shape.model.request.ADShapeCreateRequest;
import udpm.hn.metu.core.admin.shape.model.request.ADShapeRequest;
import udpm.hn.metu.core.admin.shape.model.request.ADShapeStatusUpdateRequest;
import udpm.hn.metu.core.admin.shape.service.ADShapeService;
import udpm.hn.metu.infrastructure.constant.MappingConstant;
import udpm.hn.metu.utils.Helper;

@RestController
@RequestMapping(MappingConstant.API_ADMIN_SHAPE)
@RequiredArgsConstructor
public class ADShapeController {

    @Autowired
    private final ADShapeService shapeService;

    @GetMapping("")
    public ResponseEntity<?> getAllShape( ADShapeRequest request) {
        return Helper.createResponseEntity(shapeService.getAllShape(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShapeById(@PathVariable("id") String id) {
        return Helper.createResponseEntity(shapeService.getShapeById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> addShape(@Valid @RequestBody ADShapeCreateRequest request) {
        return Helper.createResponseEntity(shapeService.addShape(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateShape(@Valid @RequestBody ADShapeCreateRequest request, @PathVariable("id") String id) {
        return Helper.createResponseEntity(shapeService.updateShape(request, id));
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateShapeByStatus(@Valid @RequestBody ADShapeStatusUpdateRequest request, @PathVariable("id") String id) {
        return Helper.createResponseEntity(shapeService.updateShapeByStatus(request, id));
    }
}
