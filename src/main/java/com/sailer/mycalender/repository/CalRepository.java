package com.sailer.mycalender.repository;

import com.sailer.mycalender.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CalRepository extends JpaRepository<Schedule, Long> {
    // LocalDateTime 리포지토리 관련 테스트용

    public List<Schedule> findAllByDateBetween(LocalDate start, LocalDate end);
    public List<Schedule> findAllByDate(LocalDate date);


}
