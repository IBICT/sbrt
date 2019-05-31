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

/**
 * Spring Data  repository for the Answer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Page<Answer> findByLegalEntityId(Pageable pageable, Long id);

    Page<Answer> findByCnaeCod(Pageable pageable, String cod);

    @Query("select answer from Answer answer where answer.user.login = ?#{principal.username}")
    List<Answer> findByUserIsCurrentUser();

    @Query("select new br.ibict.domain.AnswerSummary(a.id, a.title, a.description, a.datePublished," + 
    " a.timesSeen, q.id, l.id) from Answer a "
    + "left join a.legalEntity l " 
    + "left join a.question q")
    Page<AnswerSummary> getSummaries(Pageable pageable);

    @Query("select new br.ibict.service.dto.AnswerStatisticsDTO(a.timesSeen, a.datePublished, q.dateAsked) from Answer a left join a.question q where a.id = :id")
    AnswerStatisticsDTO getStatisticsByID(@Param("id") Long id);

    @Query("select new br.ibict.service.dto.AccumulatedStatisticsDTO(SUM(a.timesSeen), COUNT(a.id), AVG(a.datePublished - q.dateAsked)) " 
    + " from Answer a left join a.question q")
    AccumulatedStatisticsDTO getAccumulatedStatistics();

    @Query("select new br.ibict.service.dto.AccumulatedStatisticsDTO(SUM(a.timesSeen), COUNT(a.id), AVG(a.datePublished - q.dateAsked)) " 
    + " from Answer a "
    + " left join a.question q "
    + " left join a.cnae c "
    + " where c.cod = :cnae")
    AccumulatedStatisticsDTO getAccumulatedStatisticsByCnae(@Param("cnae") String cnae);

    @Query("select new br.ibict.service.dto.AccumulatedStatisticsDTO(SUM(a.timesSeen), COUNT(a.id), AVG(a.datePublished - q.dateAsked)) " 
    + " from Answer a "
    + " left join a.question q "
    + " left join a.legalEntity l "
    + " where l.id = :id")
	AccumulatedStatisticsDTO getAccumulatedStatisticsByLegalEntity(@Param("id") Long id);
}
