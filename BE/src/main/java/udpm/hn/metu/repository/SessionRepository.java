package udpm.hn.metu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<udpm.hn.metu.entity.Session, String> {
}
