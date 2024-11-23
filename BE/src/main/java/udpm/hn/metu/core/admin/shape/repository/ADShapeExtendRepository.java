package udpm.hn.metu.core.admin.shape.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udpm.hn.metu.core.admin.shape.model.request.ADShapeRequest;
import udpm.hn.metu.core.admin.shape.model.response.ADShapeResponse;
import udpm.hn.metu.repository.ShapeRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ADShapeExtendRepository extends ShapeRepository {

    @Query(value = """
            SELECT 
                ROW_NUMBER() OVER (ORDER BY s.id DESC) AS STT,
                s.id AS shapeId,
                s.code AS shapeCode,
                s.name AS shapeName,
                s.class_name AS shapeClassName,
                s.icon_url AS shapeIconUrl,
                s.description AS shapeDescription,
                s.status AS shapeStatus
            FROM 
                shape s
            WHERE
                (:#{#request.shapeCode} IS NULL OR s.code LIKE %:#{#request.shapeCode}%)
                AND (:#{#request.shapeName} IS NULL OR s.name LIKE %:#{#request.shapeName}%)
                AND (:#{#request.shapeStatus} IS NULL OR s.status = :#{#request.shapeStatus})                  
            """,
            countQuery = """
                    SELECT 
                        COUNT(s.id)
                    FROM
                        shape s 
                    WHERE
                        (:#{#request.shapeCode} IS NULL OR s.code LIKE %:#{#request.shapeCode}%)
                         AND (:#{#request.shapeName} IS NULL OR s.name LIKE %:#{#request.shapeName}%)
                         AND (:#{#request.shapeStatus} IS NULL OR s.status = :#{#request.shapeStatus})                            
                    """,
            nativeQuery = true)
    Page<ADShapeResponse> getAllShapeByStatus(Pageable pageable, @Param("request") ADShapeRequest request);

    @Query(value = """
            SELECT
                    ROW_NUMBER() OVER(ORDER BY s.id DESC) AS stt,
                    s.id AS shapeId,
                    s.code AS shapeCode,
                    s.name AS shapeName,
                    s.class_name AS shapeClassName,
                    s.icon_url AS shapeIconUrl,
                    s.description AS shapeDescription,
                    s.status AS shapeStatus
            FROM
                    shape s
            WHERE
                    s.id = :id                                                                                                                                                                            
            """, nativeQuery = true)
    Optional<ADShapeResponse> getAllShapeById(@Param("id") String id);

}
