package br.ibict.web.rest;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.ibict.service.StatisticsService;
import br.ibict.service.dto.AccumulatedStatisticsDTO;
import br.ibict.service.dto.AnswerStatisticsDTO;
import br.ibict.service.dto.GeneralStatisticsDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/statistics")
public class StatisticsResource {
    private final Logger log = LoggerFactory.getLogger(StatisticsResource.class);

    private StatisticsService statisticsService;

    public StatisticsResource(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * GET  /statistics/ : get general statistics 
     *
     * @return the ResponseEntity with status 200 (OK) and an object with statistics as the body
     */
    @GetMapping(value="/")
    public ResponseEntity<GeneralStatisticsDTO> getGeneralStatistics() {
        log.debug("REST request to get general statistics");
        return ResponseEntity.ok().body(statisticsService.getGeneralStatistics());
    }

    /**
     * GET  /statistics/answer/:id : get statistics regarding an answer
     *
     * @param id: the answer id
     * @return the ResponseEntity with status 200 (OK) and an object with statistics as the body
     * or with status 404 (Not Found) if there is no answer with :id
     */
    @GetMapping(value="/answer/{id}")
    public ResponseEntity<AnswerStatisticsDTO> getStatisticsForAnswer(@PathVariable Long id) {
        log.debug("REST request to get statistics for answer : {}", id);
        return ResponseEntity.ok().body(statisticsService.getStatisticsByID(id));
    }

    /**
     * GET  /statistics/cnae/:cnae : get statistics regarding a CNAE category
     *
     * @param cnae: the CNAE code
     * @return the ResponseEntity with status 200 (OK) and an object with statistics as the body
     * or with status 404 (Not Found) if there is no CNAE with code :cnae
     */
    @GetMapping(value="/cnae/{cnae}")
    public ResponseEntity<AccumulatedStatisticsDTO> getStatisticsByCnae(@PathVariable String cnae) {
        log.debug("REST request to get statistics for cnae : {}", cnae);
        return ResponseEntity.ok().body(statisticsService.getAccumulatedStatisticsByCnae(cnae));
    }

    /**
     * GET  /statistics/legalEntity/:id : get statistics regarding a Legal Entity
     *
     * @param id: the legal entity id
     * @return the ResponseEntity with status 200 (OK) and an object with statistics as the body
     * or with status 404 (Not Found) if there is no legal entity with :id
     */
    @GetMapping(value="/legalEntity/{id}")
    public ResponseEntity<AccumulatedStatisticsDTO> getStatisticsByLegalEntity(@PathVariable Long id) {
        log.debug("REST request to get statistics for legal entity : {}", id);
        return ResponseEntity.ok().body(statisticsService.getAccumulatedStatisticsByLegalEntity(id));
    }

    /**
     * Captures EntityNotFoundException and gives a 404 response to the caller that caused it
     * @param ex
     * @return 404 Response
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


}