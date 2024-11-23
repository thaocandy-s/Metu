package udpm.hn.metu.core.embed.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udpm.hn.metu.core.embed.model.request.EmbedMenuRequest;
import udpm.hn.metu.core.embed.model.response.EmbedMenuResponse;
import udpm.hn.metu.repository.MenuRepository;

import java.util.Optional;

@Repository
public interface EmbedMenuRepository extends MenuRepository {

    @Query(value = """
          SELECT
              mn.id,
              mn.code,
              po.class_name as position_class_name,
              sh.class_name as shape_class_name,
              mn.icon_color,
              mn.text_color,
              mn.background_color,
              mn.opacity,
              mn.appear_from,
              mn.appear_to,
              mn.scroll_type,
              mn.scroll_value,
              mn.time_show,
              mn.time_hidden
          FROM Menu mn
               LEFT JOIN menu_display_on md ON mn.id = md.menu_id
               JOIN user u ON u.id = mn.user_id
               JOIN business b ON b.id = u.business_id
               JOIN position po ON po.id = mn.position_id
               JOIN shape sh ON sh.id = mn.shape_id
          WHERE mn.status = 0
                AND ( b.status = 0 AND b.code = :#{#req.businessCode} )
                AND (HOUR(CURRENT_TIME()) BETWEEN mn.appear_from AND mn.appear_to)
                AND ( mn.device_type = 'BOTH' OR mn.device_type = :#{#req.deviceType} )
                AND ( mn.display_option = 'ALL_PATH'
                      OR ( mn.display_option = 'EXACT_PATH' AND md.value = :#{#req.currentUrl} )
                      OR ( mn.display_option = 'CONTAIN_PATH' AND :#{#req.currentUrl} LIKE CONCAT('%', md.value, '%')))
            """, nativeQuery = true)
    Optional<EmbedMenuResponse> getEmbedMenu(EmbedMenuRequest req);


    @Query(value = """
          SELECT
              mn.id,
              mn.code,
              po.class_name as position_class_name,
              sh.class_name as shape_class_name,
              mn.icon_color,
              mn.text_color,
              mn.background_color,
              mn.opacity,
              mn.appear_from,
              mn.appear_to,
              mn.scroll_type,
              mn.scroll_value,
              mn.time_show,
              mn.time_hidden
          FROM Menu mn
               LEFT JOIN menu_display_on md ON mn.id = md.menu_id
               JOIN user u ON u.id = mn.user_id
               JOIN business b ON b.id = u.business_id
               JOIN position po ON po.id = mn.position_id
               JOIN shape sh ON sh.id = mn.shape_id
          WHERE mn.code = :code
            """, nativeQuery = true)
    Optional<EmbedMenuResponse> findEmbedMenuByCode(String code);

}
