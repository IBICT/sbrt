package br.ibict.repository;

import br.ibict.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "select distinct person from Person person left join fetch person.legalEntities",
        countQuery = "select count(distinct person) from Person person")
    Page<Person> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct person from Person person left join fetch person.legalEntities")
    List<Person> findAllWithEagerRelationships();

    @Query("select person from Person person left join fetch person.legalEntities where person.id =:id")
    Optional<Person> findOneWithEagerRelationships(@Param("id") Long id);

}
