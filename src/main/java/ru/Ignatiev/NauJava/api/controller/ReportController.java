package ru.Ignatiev.NauJava.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Ignatiev.NauJava.domain.impl.ReportServiceImpl;

@Controller
public class ReportController {

    private final ReportServiceImpl reportService;

    @Autowired
    ReportController(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/report/async")
    @ResponseBody
    public String createReport() {
        var reportId = reportService.createAsyncReport();
        if (reportId == null) {
            return "Report was not formed succesfully" + reportId;
        }
        return "Report was formed with id " + reportId;
    }

    @GetMapping("/report/{id}")
    public String returnReport(@PathVariable Long id, Model model) {
        var reportEntity = reportService.getReport(id);
        if (reportEntity == null) {
            return "report-error";
        }
        model.addAttribute("userCount", reportEntity.getUserCount());
        model.addAttribute("userCountDuration", reportEntity.getUserCountDuration());
        model.addAttribute("totalTime", reportEntity.getTotalTime());
        model.addAttribute("userListDuration", reportEntity.getUserListDuration());
        model.addAttribute("userList", reportEntity.getUserListNames());
        return "report";
    }
}