package br.ibict.repository;

import br.ibict.domain.Cnae;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cnae entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CnaeRepository extends JpaRepository<Cnae, Long> {

}
