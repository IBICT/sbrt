package br.ibict.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ibict.repository.AnswerRepository;
import br.ibict.repository.CnaeRepository;
import br.ibict.repository.LegalEntityRepository;
import br.ibict.repository.UserRepository;
import br.ibict.service.StatisticsService;
import br.ibict.service.dto.AccumulatedStatisticsDTO;
import br.ibict.service.dto.AnswerStatisticsDTO;
import br.ibict.service.dto.GeneralStatisticsDTO;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    private AnswerRepository answerRepository;
    private UserRepository userRepository;
    private LegalEntityRepository legalEntityRepository;
    private CnaeRepository cnaeRepository;

    public StatisticsServiceImpl(AnswerRepository answerRepository,
                UserRepository userRepository, LegalEntityRepository
                legalEntityRepository, CnaeRepository cnaeRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.legalEntityRepository = legalEntityRepository;
        this.cnaeRepository = cnaeRepository;
    }

    public AnswerStatisticsDTO getStatisticsByID(Long id) {
        if(!this.answerRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        return this.answerRepository.getStatisticsByID(id);
    }

    public AccumulatedStatisticsDTO getAccumulatedStatisticsByCnae(String cnae) {
        if(!this.cnaeRepository.existsByCod(cnae)) {
            throw new EntityNotFoundException();
        }
        return this.answerRepository.getAccumulatedStatisticsByCnae(cnae);
    }

	public AccumulatedStatisticsDTO getAccumulatedStatisticsByLegalEntity(Long id) {
        if(!this.legalEntityRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        return this.answerRepository.getAccumulatedStatisticsByLegalEntity(id);
    }

    public GeneralStatisticsDTO getGeneralStatistics() {
        AccumulatedStatisticsDTO accStats = this.answerRepository.getAccumulatedStatistics();
        GeneralStatisticsDTO genStats = new GeneralStatisticsDTO(accStats);
        genStats.setNumberOfUsers(userRepository.count());

        return genStats;
    }

}