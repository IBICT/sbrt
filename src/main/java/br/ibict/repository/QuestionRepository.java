package br.ibict.repository;

import br.ibict.domain.Question;
import br.ibict.service.dto.QuestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select question from Question question where question.user.login = ?#{principal.username}")
    List<Question> findByUserIsCurrentUser();

	Page<Question> findByLegalEntityId(Pageable pageable, Long legalEntityId);

	Page<Question> findByUserId(Long id, Pageable pageable);

}
