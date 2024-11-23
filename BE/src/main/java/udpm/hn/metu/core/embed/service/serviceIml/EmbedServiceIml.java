package udpm.hn.metu.core.embed.service.serviceIml;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import udpm.hn.metu.core.common.base.ResponseObject;
import udpm.hn.metu.core.embed.model.request.EmbedMenuRequest;
import udpm.hn.metu.core.embed.model.request.InteractRequest;
import udpm.hn.metu.core.embed.model.response.EmbedButtonsResponse;
import udpm.hn.metu.core.embed.model.response.EmbedMenuDTO;
import udpm.hn.metu.core.embed.model.response.EmbedMenuResponse;
import udpm.hn.metu.core.embed.repository.EmbedButtonRepository;
import udpm.hn.metu.core.embed.repository.EmbedMenuRepository;
import udpm.hn.metu.core.embed.service.EmbedService;
import udpm.hn.metu.entity.Business;
import udpm.hn.metu.entity.Click;
import udpm.hn.metu.entity.Domain;
import udpm.hn.metu.entity.Menu;
import udpm.hn.metu.entity.SubDomain;
import udpm.hn.metu.entity.Url;
import udpm.hn.metu.infrastructure.constant.enums.Status;
import udpm.hn.metu.repository.BusinessRepository;
import udpm.hn.metu.repository.ClickRepository;
import udpm.hn.metu.repository.DomainRepository;
import udpm.hn.metu.repository.SubDomainRepository;
import udpm.hn.metu.repository.UrlRepository;
import udpm.hn.metu.utils.DomainExtractorUtil;
import udpm.hn.metu.utils.Helper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmbedServiceIml implements EmbedService {

    private final EmbedMenuRepository embedMenuRepository;

    private final EmbedButtonRepository embedButtonRepository;

    private final DomainRepository domainRepository;

    private final SubDomainRepository subDomainRepository;

    private final BusinessRepository businessRepository;

    private final UrlRepository urlRepository;

    private final ClickRepository clickRepository;



    @Override
    public ResponseObject<?> getEmbedMenu(@RequestBody EmbedMenuRequest req) {
        Optional<EmbedMenuResponse> menuOptional = embedMenuRepository.getEmbedMenu(req);
        if (menuOptional.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "No satisfactory menu exists.");
        }
        EmbedMenuResponse menu = menuOptional.get();
        List<EmbedButtonsResponse> buttons = embedButtonRepository.findAllByMenuId(menu.getId());
        if (buttons.isEmpty()) {
            return new ResponseObject<>(menu, HttpStatus.NO_CONTENT, "No satisfactory buttons exists.");
        } else {
            Domain domain = saveDomainInfo(req);
            Url url = saveMenuDisplayInfo(req.currentUrl, domain, menu.getId());
            EmbedMenuDTO response = new EmbedMenuDTO(menu, buttons, url.getId());
            return new ResponseObject<>(response, HttpStatus.OK, "A satisfactory menu exists.");
        }
    }

    @Override
    public ResponseObject<?> saveInteractInfo(InteractRequest req) {
            Click click = new Click();
            click.setButtonId(req.buttonId);
            click.setUrl(urlRepository.findById(req.currentUrlId).orElse(null));
            click.setCreateAt(System.currentTimeMillis());
            click = clickRepository.save(click);
            return new ResponseObject<>(click, HttpStatus.CREATED, "Save interact info success");
    }

    private Domain saveDomainInfo(EmbedMenuRequest req) {
        Business business = businessRepository.findByCode(req.businessCode).orElse(null);
        if (business != null) {
            Map<String, String> domains = DomainExtractorUtil.getDomainAndSubdomain(req.currentUrl);
            Optional<Domain> domain = domainRepository.findByNameAndBusiness_Code(domains.get("domain"), req.businessCode);
            if (domain.isPresent()) {
                Optional<SubDomain> subDomain = subDomainRepository.findByNameAndDomain_Name(domains.get("subdomain"), domains.get("domain"));
                if (subDomain.isEmpty()) {
                    if (!domains.get("subdomain").isBlank()) {
                        SubDomain newSubDomain = new SubDomain(domain.get(), Helper.generateCode("SDM"), domains.get("subdomain"));
                        subDomainRepository.save(newSubDomain);
                    }
                }
                return domain.get();
            } else {
                Domain newDomain = new Domain(business, Helper.generateCode("DM"), domains.get("domain"), Status.ACTIVE);
                domainRepository.save(newDomain);
                if (!domains.get("subdomain").isBlank()) {
                    SubDomain newSubDomain = new SubDomain(newDomain, Helper.generateCode("SDM"), domains.get("subdomain"));
                    subDomainRepository.save(newSubDomain);
                }
                return newDomain;
            }
        }
        return null;
    }

    private Url saveMenuDisplayInfo(String currentUrl, Domain domain, String menuId){
        Url url = new Url();
        url.setMenu(embedMenuRepository.findById(menuId).orElse(null));
        url.setDomain(domain);
        url.setValue(currentUrl);
        url.setRelativeValue(currentUrl.split("\\?")[0]);

        Map<String, String> utmParams = DomainExtractorUtil.utmParams(currentUrl);
        url.setUtmSource(utmParams.get("utm_source"));
        url.setUtmMedium(utmParams.get("utm_medium"));
        url.setUtmCampaign(utmParams.get("utm_campaign"));
        url.setUtmContent(utmParams.get("utm_content"));
        url.setUtmTerm(utmParams.get("utm_term"));

        url = urlRepository.save(url);

        return url;
    }


    @Override
    public ResponseObject<?> findMenuByCode(String menuCode) {
        Optional<EmbedMenuResponse> menuOptional = embedMenuRepository.findEmbedMenuByCode(menuCode);
        if(menuOptional.isEmpty()){
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "No satisfactory menu exists");
        }
        EmbedMenuResponse menu = menuOptional.get();
        List<EmbedButtonsResponse> buttons = embedButtonRepository.findAllByMenuId(menu.getId());
        if (buttons.isEmpty()) {
            return new ResponseObject<>(menu, HttpStatus.NO_CONTENT, "No satisfactory menu exists.");
        } else {
            EmbedMenuDTO response = new EmbedMenuDTO(menu, buttons, null);
            return new ResponseObject<>(response, HttpStatus.OK, "A satisfactory menu exists.");
        }
    }


}
