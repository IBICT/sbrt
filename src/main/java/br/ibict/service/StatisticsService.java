package br.ibict.service;

import br.ibict.service.dto.AccumulatedStatisticsDTO;
import br.ibict.service.dto.AnswerStatisticsDTO;
import br.ibict.service.dto.GeneralStatisticsDTO;

public interface StatisticsService {

    public AnswerStatisticsDTO getStatisticsByID(Long id);

    public GeneralStatisticsDTO getGeneralStatistics();

    public AccumulatedStatisticsDTO getAccumulatedStatisticsByCnae(String cnae);

	public AccumulatedStatisticsDTO getAccumulatedStatisticsByLegalEntity(Long id);


}