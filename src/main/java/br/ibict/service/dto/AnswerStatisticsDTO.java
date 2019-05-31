package br.ibict.service.dto;

import java.time.Duration;
import java.time.Instant;

public class AnswerStatisticsDTO {

    private Integer timesSeen;

    private Long timeToAnswer;

    public AnswerStatisticsDTO(Integer timesSeen, Instant datePublished, Instant dateAsked) {
        this.timesSeen = timesSeen;
        this.timeToAnswer = Duration.between(dateAsked, datePublished).toMillis();
    }

    public Integer getTimesSeen() {
        return timesSeen;
    }

    public void setTimesSeen(Integer timesSeen) {
        this.timesSeen = timesSeen;
    }

    public Long getTimeToAnswer() {
        return timeToAnswer;
    }

    public void setTimeToAnswer(Long timeToAnswer) {
        this.timeToAnswer = timeToAnswer;
    }

}