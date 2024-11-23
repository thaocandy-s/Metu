package udpm.hn.metu.core.embed.service;

import udpm.hn.metu.core.common.base.ResponseObject;
import udpm.hn.metu.core.embed.model.request.EmbedMenuRequest;
import udpm.hn.metu.core.embed.model.request.InteractRequest;

public interface EmbedService {

    ResponseObject<?> getEmbedMenu(EmbedMenuRequest req);

    ResponseObject<?> saveInteractInfo(InteractRequest req);

    ResponseObject<?> findMenuByCode(String menuCode);



}
