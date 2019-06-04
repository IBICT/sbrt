package br.ibict.repository;

import br.ibict.domain.Answer;
import br.ibict.domain.AnswerSummary;
import br.ibict.service.dto.AccumulatedStatisticsDTO;
import br.ibict.service.dto.AnswerStatisticsDTO;
import br.ibict.service.dto.GeneralStatisticsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Answer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findFirstByQuestionIdOrderByDatePublished(Long questionId);

    @Query("select new br.ibict.domain.AnswerSummary(a.id, a.title, a.description, a.datePublished," + 
    " a.timesSeen, a.question.id, a.legalEntity.id) from Answer a "
    + "where a.legalEntity.id = :id "
    + "and a.isReferentialOnly = false")
    Page<AnswerSummary> getByLegalEntityId(Pageable pageable, @Param("id") Long id);

    @Query("select new br.ibict.domain.AnswerSummary(a.id, a.title, a.description, a.datePublished," + 
    " a.timesSeen, a.question.id, a.legalEntity.id) from Answer a "
    + "left join a.cnae c "
    + "where c.cod = :cod "
    + "and a.isReferentialOnly = false")
    Page<AnswerSummary> getByCnaeCod(Pageable pageable, @Param("cod") String cod);

    @Query("select answer from Answer answer where answer.user.login = ?#{principal.username}")
    List<Answer> findByUserIsCurrentUser();

    @Query("select new br.ibict.domain.AnswerSummary(a.id, a.title, a.description, a.datePublished," + 
    " a.timesSeen, a.question.id, a.legalEntity.id) from Answer a "
    + "where a.isReferentialOnly = false")
    Page<AnswerSummary> getSummaries(Pageable pageable);

    @Query("select new br.ibict.service.dto.AnswerStatisticsDTO(a.timesSeen, a.datePublished, q.dateAsked) from Answer a left join a.question q where a.id = :id")
    AnswerStatisticsDTO getStatisticsByID(@Param("id") Long id);

    @Query("select new br.ibict.service.dto.AccumulatedStatisticsDTO(SUM(a.timesSeen), COUNT(a.id), AVG(a.datePublished - q.dateAsked)) " 
    + " from Answer a left join a.question q "
    + "where a.isReferentialOnly = false")
    AccumulatedStatisticsDTO getAccumulatedStatistics();

    @Query("select new br.ibict.service.dto.AccumulatedStatisticsDTO(SUM(a.timesSeen), COUNT(a.id), AVG(a.datePublished - q.dateAsked)) " 
    + " from Answer a "
    + " left join a.question q "
    + " left join a.cnae c "
    + " where c.cod = :cnae "
    + "and a.isReferentialOnly = false")
    AccumulatedStatisticsDTO getAccumulatedStatisticsByCnae(@Param("cnae") String cnae);

    @Query("select new br.ibict.service.dto.AccumulatedStatisticsDTO(SUM(a.timesSeen), COUNT(a.id), AVG(a.datePublished - q.dateAsked)) " 
    + " from Answer a "
    + " left join a.question q "
    + " where a.legalEntity.id = :id "
    + "and a.isReferentialOnly = false")
	AccumulatedStatisticsDTO getAccumulatedStatisticsByLegalEntity(@Param("id") Long id);
}
