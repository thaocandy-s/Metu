package udpm.hn.metu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udpm.hn.metu.entity.Popup;

@Repository
public interface PopupRepository extends JpaRepository<Popup, String> {
}
