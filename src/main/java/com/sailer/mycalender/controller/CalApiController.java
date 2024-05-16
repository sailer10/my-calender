package com.sailer.mycalender.controller;

import com.sailer.mycalender.domain.Schedule;
import com.sailer.mycalender.dto.AddScheduleRequest;
import com.sailer.mycalender.dto.ScheduleListOfDayResponse;
import com.sailer.mycalender.dto.ScheduleListResponse;
import com.sailer.mycalender.dto.UpdateScheduleRequest;
import com.sailer.mycalender.service.CalService;
import com.sailer.mycalender.statics.CAL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalApiController {

    private final CalService calService;

    @PostMapping(CAL.CAL_API)
    public ResponseEntity<Schedule> saveSchedule(@RequestBody AddScheduleRequest request) {
        Schedule savedSchedule = calService.save(request);
        // status=200, body=savedSchedule 로 post 전송
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSchedule);
    }

    // Test 용 연도별 일정 가져오기
    @GetMapping(CAL.CAL_API + "/{year}")
    public ResponseEntity<List<ScheduleListResponse>> getSchedules(@PathVariable(name="year") String year) {
        List<ScheduleListResponse> schedules = calService.findByYear(year).stream().map(ScheduleListResponse::new).toList();

        return ResponseEntity.ok().body(schedules);
    }

    // 한달치 스케쥴 전부 가져오기
    @GetMapping(CAL.CAL_API + "/{year}/{month}")
    public ResponseEntity<List<ScheduleListResponse>> getSchedules(@PathVariable(name = "year") String year,
                                                                   @PathVariable(name = "month") String month) {
        List<ScheduleListResponse> schedules = calService.findByMonth(year, month).stream().map(ScheduleListResponse::new).toList();
        return ResponseEntity.ok().body(schedules);
    }

    // 하루치 스케쥴 가져오기
    @GetMapping(CAL.CAL_API + "/{year}/{month}/{day}")
    public ResponseEntity<List<ScheduleListOfDayResponse>> getSchedules(@PathVariable(name = "year") String year,
                                                                        @PathVariable(name = "month") String month,
                                                                        @PathVariable(name = "day") String day) {
        List<ScheduleListOfDayResponse> schedules = calService.findByDay(year, month, day).stream().map(ScheduleListOfDayResponse::new).toList();
        return ResponseEntity.ok().body(schedules);
    }

    @PutMapping(CAL.CAL_API + "/{id}")
    public ResponseEntity<UpdateScheduleRequest> updateSchedule(@PathVariable(name="id") long id,
                                               @RequestBody UpdateScheduleRequest request) {
        Schedule schedule = calService.update(id, request);
        UpdateScheduleRequest response = new UpdateScheduleRequest(schedule);

        return ResponseEntity.ok().body(response);
    }

    /*
    *     @PutMapping(CAL.CAL_API + "/{id}")
            public ResponseEntity<Void> updateSchedule(@PathVariable(name="id") long id,
                                                       @RequestBody UpdateScheduleRequest request) {
                calService.update(id, request);

            return ResponseEntity.ok().build();
    }
    * */

    @DeleteMapping(CAL.CAL_API + "/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable(name = "id") long id) {
        calService.delete(id);
        return ResponseEntity.ok().build();
    }




}
