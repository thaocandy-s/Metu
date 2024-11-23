package udpm.hn.metu.core.embed.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import udpm.hn.metu.core.embed.model.request.EmbedMenuRequest;
import udpm.hn.metu.core.embed.model.request.InteractRequest;
import udpm.hn.metu.core.embed.service.EmbedService;
import udpm.hn.metu.infrastructure.constant.MappingConstant;
import udpm.hn.metu.utils.Helper;

@RestController
@RequestMapping(MappingConstant.API_EMBED_PREFIX)
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmbedRestController {

    private final EmbedService embedService;

    @GetMapping("/menu")
    public ResponseEntity<?> getEmbedMenu(final EmbedMenuRequest request){
        return Helper.createResponseEntity(embedService.getEmbedMenu(request));
    }

    @PostMapping("/save-interact-info")
    public ResponseEntity<?> saveInteractInfo(@RequestBody InteractRequest request){
        return Helper.createResponseEntity(embedService.saveInteractInfo(request));
    }

    @GetMapping("/preview-menu")
    public ResponseEntity<?> getPreviewEmbedMenu(final String menuId){
        return Helper.createResponseEntity(embedService.findMenuByCode(menuId));
    }


}
