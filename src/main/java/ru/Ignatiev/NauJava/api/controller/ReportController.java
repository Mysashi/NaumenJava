package ru.Ignatiev.NauJava.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import ru.Ignatiev.NauJava.domain.impl.ReportServiceImpl;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/report")
public class ReportController {

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);
    private final ReportServiceImpl reportService;

    ReportController(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    @ResponseBody
    public String createReport(String reportContext) {
        log.info("Report was successfully created!");
        return reportService.createReport(reportContext);
    }

    @GetMapping("/async")
    public String formReportAsync(Model model)throws ExecutionException, InterruptedException {
       Context context = reportService.createAsyncAndFormContext().get();
       model.addAttribute("userCount", context.getVariable("userCount"));
       model.addAttribute("totalTime", context.getVariable("totalTime"));
       model.addAttribute("userList", context.getVariable("userList"));
       model.addAttribute("userCountDuration", context.getVariable("userCountDuration"));
       model.addAttribute("userListDuration", context.getVariable("userListDuration"));
       return "report";
    }

    @PostMapping("/context")
    public String getReportContext(Long id) {
        return reportService.getReportContext(id);
    }
}
