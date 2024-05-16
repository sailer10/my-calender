package com.sailer.mycalender.dto;

import com.sailer.mycalender.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateScheduleRequest {
    private String content;
    private LocalDate date;
    private boolean completed;

    public UpdateScheduleRequest(Schedule schedule) {
        this.content = schedule.getContent();
        this.date = schedule.getDate();
        this.completed = schedule.isCompleted();
    }
}
