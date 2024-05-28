package com.sailer.mycalender.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "content")
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate date;

    // 완료되었는지 체크
    @Column(name = "is_completed")
    private boolean isCompleted;

    @Builder
    public Schedule(String content, LocalDate date, boolean isCompleted) {
        this.content = content;
        this.date = date;
        this.isCompleted = isCompleted;
    }

    public void update (String content, LocalDate date, boolean isCompleted) {
        this.content = content;
        this.date = date;
        this.isCompleted = isCompleted;
    }

    public void update(String content, boolean isCompleted) {
        this.content = content;
        this.isCompleted = isCompleted;
    }
}
