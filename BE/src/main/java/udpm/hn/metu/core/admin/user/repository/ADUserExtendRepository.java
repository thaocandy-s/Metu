package udpm.hn.metu.core.admin.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.metu.core.admin.user.model.request.ADSearchUserRepuest;
import udpm.hn.metu.core.admin.user.model.response.ADUserResponse;
import udpm.hn.metu.entity.User;
import udpm.hn.metu.repository.UserRepository;

import java.util.Optional;

@Repository
public interface ADUserExtendRepository extends UserRepository {

    @Query(value = """
            SELECT DISTINCT
            ROW_NUMBER() OVER(ORDER BY u.id DESC) AS stt,
            u.id AS id,
            u.code AS code,
            u.full_name AS name,
            b.name AS nameBusiness,
            u.email AS email,
            u.role AS nameRole,
            u.address AS address,
            u.avatar_image AS avatarImage,
            u.rating AS rating,
            u.status AS status,
            u.is_online AS isOnline,
            u.last_time_active AS lastTimeActive
            FROM metu.user u
            LEFT JOIN business b ON b.id = u.business_id
            WHERE (:#{#req.search} IS NULL
                   OR (u.full_name LIKE %:#{#req.search}%
                       OR u.address LIKE %:#{#req.search}%
                       OR u.code LIKE %:#{#req.search}%
                       OR u.email LIKE %:#{#req.search}%))
            """, countQuery = """
             SELECT COUNT(*)
             FROM metu.user u
             LEFT JOIN business b ON b.id = u.business_id
             WHERE (:#{#req.search} IS NULL
                    OR (u.full_name LIKE %:#{#req.search}%
                        OR u.address LIKE %:#{#req.search}%
                        OR u.code LIKE %:#{#req.search}%
                        OR u.email LIKE %:#{#req.search}%))
            """, nativeQuery = true)
    Page<ADUserResponse> getAllUser(Pageable pageable, @Param("req") ADSearchUserRepuest req);


    @Query(value = """
            select
            ROW_NUMBER() OVER(order by u.code desc) as stt,
           u.id AS id,
            u.code AS code,
            u.full_name AS name,
            b.name AS nameBusiness,
            u.email AS email,
            u.role AS nameRole,
            u.address AS address,
            u.avatar_image AS avatarImage,
            u.rating AS rating,
            u.status AS status,
            u.is_online AS isOnline,
            u.last_time_active AS lastTimeActive
            from metu.user u
            left join business b
            on b.id = u.business_id
            where u.id = :req
            """, nativeQuery = true)
    Optional<ADUserResponse> getUserById(@Param("req") String req);


}
