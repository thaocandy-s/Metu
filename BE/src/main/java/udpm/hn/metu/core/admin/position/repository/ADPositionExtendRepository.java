package udpm.hn.metu.core.admin.position.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.metu.core.admin.position.model.request.ADPositionRequest;
import udpm.hn.metu.core.admin.position.model.response.ADPositionResponse;
import udpm.hn.metu.repository.PositionRepository;

import java.util.Optional;

@Repository
public interface ADPositionExtendRepository extends PositionRepository {

    @Query(value = """
            SELECT
                ROW_NUMBER() OVER (ORDER BY p.id DESC) AS STT,
                p.id AS positionId,
                p.code AS positionCode,
                p.name AS positionName,
                p.class_name AS positionClassName,
                p.icon_url AS positionIconUrl,
                p.description AS positionDescription,
                p.status AS positionStatus
            FROM
                position p
            WHERE
                (:#{#request.positionCode} IS NULL OR p.code LIKE %:#{#request.positionCode}%)
                AND (:#{#request.positionName} IS NULL OR p.name LIKE %:#{#request.positionName}%)
                AND (:#{#request.positionStatus} IS NULL OR p.status = :#{#request.positionStatus})                  
            """,
            countQuery = """
            SELECT 
                COUNT(p.id)
            FROM
                position p 
            WHERE
                (:#{#request.positionCode} IS NULL OR p.code LIKE %:#{#request.positionCode}%)
                AND (:#{#request.positionName} IS NULL OR p.name LIKE %:#{#request.positionName}%)
                AND (:#{#request.positionStatus} IS NULL OR p.status = :#{#request.positionStatus})                            
            """,
            nativeQuery = true)
    Page<ADPositionResponse> getAllPosition(Pageable pageable, @Param("request") ADPositionRequest request);

    @Query(value = """
                SELECT
                    ROW_NUMBER() OVER(ORDER BY p.id DESC) AS stt,
                    p.id AS positionId,
                    p.code AS positionCode,
                    p.name AS positionName,
                    p.class_name AS positionClassName,
                    p.icon_url AS positionIconUrl,
                    p.description AS positionDescription,
                    p.status AS positionStatus
                FROM
                    position p
                WHERE
                    p.id = :id                                                                                                                                                                            
            """, nativeQuery = true)
    Optional<ADPositionResponse> getAllPositionById(@Param("id") String id);
}
