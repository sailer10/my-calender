package com.sailer.mycalender.dto;

import com.sailer.mycalender.domain.Schedule;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleListOfDayResponse {
    private final long id;
    private final String content;
    private final LocalDate date;
    private final boolean isCompleted;

    public ScheduleListOfDayResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.content = schedule.getContent();
        this.date = schedule.getDate();
        this.isCompleted = schedule.isCompleted();
    }
}
