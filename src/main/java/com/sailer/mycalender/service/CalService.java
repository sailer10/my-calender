package com.sailer.mycalender.service;

import com.sailer.mycalender.domain.Schedule;
import com.sailer.mycalender.dto.AddScheduleRequest;
import com.sailer.mycalender.dto.UpdateScheduleRequest;
import com.sailer.mycalender.repository.CalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.StringTokenizer;

@Service
@RequiredArgsConstructor
public class CalService {

    private final CalRepository calRepository;

    public Schedule save(AddScheduleRequest request) {
        return calRepository.save(request.toEntity());
    }

    public List<Schedule> findAll() {
        return calRepository.findAll();
    }

    public List<Schedule> findByYear(String syear) {
        int year = Integer.parseInt(syear);

        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        return calRepository.findAllByDateBetween(start, end);
    }

    public List<Schedule> findByMonth(String syear, String smonth) {
        int year = Integer.parseInt(syear);
        int month = Integer.parseInt(smonth);

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return calRepository.findAllByDateBetween(start, end);
    }

    public List<Schedule> findByDay(String syear, String smonth, String sday) {
        int year = Integer.parseInt(syear);
        int month = Integer.parseInt(smonth);
        int day = Integer.parseInt(sday);

        LocalDate date = LocalDate.of(year, month, day);
        return calRepository.findAllByDate(date);
    }

    @Transactional
    public Schedule update(long id, UpdateScheduleRequest request) {
        Schedule schedule = calRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found" + id));

        if (request.getDate() != null)
            schedule.update(request.getContent(), request.getDate(), request.isCompleted());
        else
            schedule.update(request.getContent(), request.isCompleted());

        return schedule;
    }

    public void delete(long id) {
        calRepository.deleteById(id);
    }


}
