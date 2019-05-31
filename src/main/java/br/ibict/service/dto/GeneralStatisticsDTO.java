package br.ibict.service.dto;

public class GeneralStatisticsDTO extends AccumulatedStatisticsDTO {

    private Long numberOfUsers;

    public GeneralStatisticsDTO(Long timesSeen, Long numberOfAnswers, Double averageTime) {
        super(timesSeen, numberOfAnswers, averageTime);
    }

    public GeneralStatisticsDTO(AccumulatedStatisticsDTO accStats) {
        super(accStats.timesSeen, accStats.numberOfAnswers, accStats.averageTime);
    }

    public Long getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(Long numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }


}