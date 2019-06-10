package br.ibict.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ibict.domain.AnswerSummary;

@SuppressWarnings("unused")
@Repository
public interface AnswerSummaryRepository extends JpaRepository<AnswerSummary, Long> {

    Page<AnswerSummary> findByLegalEntityId(Pageable pageable, Long legalEntityId);

    Page<AnswerSummary> getByCnaeCod(Pageable pageable, String cod);

}