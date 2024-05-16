package com.sailer.mycalender.dto;

import com.sailer.mycalender.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddScheduleRequest {

    private String title;
    private String content;
    private LocalDate date;
    private boolean isCompleted;

    public Schedule toEntity() {
        return Schedule.builder()
                .content(content)
                .date(date)
                .isCompleted(isCompleted)
                .build();
    }
}
