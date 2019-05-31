package br.ibict.service.dto;

public class AccumulatedStatisticsDTO {

    protected Long timesSeen;

    protected Long numberOfAnswers;

    protected Double averageTime;


    public AccumulatedStatisticsDTO(Long timesSeen, Long numberOfAnswers, Double averageTime) {
        this.timesSeen = timesSeen;
        this.numberOfAnswers = numberOfAnswers;
        this.averageTime = averageTime;
    }

    public Long getTimesSeen() {
        return timesSeen;
    }

    public void setTimesSeen(Long timesSeen) {
        this.timesSeen = timesSeen;
    }

    public Long getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(Long numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    public Double getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Double averageTime) {
        this.averageTime = averageTime;
    }

}