package udpm.hn.metu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udpm.hn.metu.entity.Domain;

import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<Domain, String> {

    Optional<Domain> findByNameAndBusiness_Code(String name, String businessCode);

}
