package udpm.hn.metu.core.manager.example.controller.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import udpm.hn.metu.entity.RefreshToken;
import udpm.hn.metu.repository.RefreshTokenRepository;

import java.util.Optional;

public interface SecurityRefreshTokenRepository extends RefreshTokenRepository {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Modifying
    @Transactional
    @Query(
            value = """
                    delete from refresh_token
                    where user_id = :userId
                    """,
            nativeQuery = true
    )
    int deleteByUserId(Long userId);

    @Query(
            value = """
                            SELECT revoked_at
                            FROM refresh_token rt
                            WHERE rt.user_id = :userId
                    """,
            nativeQuery = true
    )
    Long isRevoked(String userId);


    @Query(
            value = """
                    SELECT rt
                    FROM RefreshToken rt
                    WHERE rt.userId = :userId
                    """
    )
    RefreshToken getRefreshTokenByUserId(String userId);

    Optional<RefreshToken> findByUserId(String userId);

}
