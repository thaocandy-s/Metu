package udpm.hn.metu.core.embed.repository;

import org.springframework.stereotype.Repository;
import udpm.hn.metu.core.embed.model.response.EmbedButtonsResponse;
import udpm.hn.metu.repository.ButtonRepository;

import java.util.List;

@Repository
public interface EmbedButtonRepository extends ButtonRepository {

    List<EmbedButtonsResponse> findAllByMenuId(String menuId);

}
