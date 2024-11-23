package udpm.hn.metu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udpm.hn.metu.entity.Domain;
import udpm.hn.metu.entity.SubDomain;

import java.util.Optional;

@Repository
public interface SubDomainRepository extends JpaRepository<SubDomain, String> {

    Optional<SubDomain> findByNameAndDomain_Name(String subDomainName, String domainName);

}
