package udpm.hn.metu.infrastructure.config.repository;

import org.springframework.stereotype.Repository;
import udpm.hn.metu.entity.BusinessType;
import udpm.hn.metu.repository.BusinessTypeRepository;

import java.util.Optional;

@Repository
public interface DBGenBusinessTypeRepository extends BusinessTypeRepository {
    Optional<BusinessType> findByCode(String individual);
}
