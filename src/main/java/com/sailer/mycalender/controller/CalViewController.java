package com.sailer.mycalender.controller;

import com.sailer.mycalender.service.CalService;
import com.sailer.mycalender.statics.CAL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.StringTokenizer;

@Controller
@RequiredArgsConstructor
public class CalViewController {

    private final CalService calService;

    @GetMapping("/")
    public String root() {
        return "redirect:" + CAL.IDX;
    }

    @GetMapping(CAL.IDX)
    public String getCalendar() {


        return CAL.IDX;
    }

    // todo: @RequestParam 3번쓰기 -> @ModelAttribute LocalDate date 로 바꾸기
    @GetMapping(CAL.SCHEDULE)
    public String getSchedules(@RequestParam(name = "year", required = false) String year,
                               @RequestParam(name = "month", required = false) String month,
                               @RequestParam(name = "day", required = false) String day,
                               Model model) {
        String date;
        // 날짜 정보를 다 넘기거나 아예 없거나만 처리
        if (year != null && month != null && day != null) {
            if (month.length() == 1)
                month = "0" + month;
            if (day.length() == 1)
                day = "0" + day;
            date = year + "-" + month + "-" + day;

        } else {
            date = LocalDate.now().toString();
        }
        model.addAttribute("date", date);
        return CAL.SCHEDULE;
    }

}
