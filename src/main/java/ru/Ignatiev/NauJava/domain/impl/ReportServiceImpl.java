package ru.Ignatiev.NauJava.domain.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import ru.Ignatiev.NauJava.domain.entity.ReportEntity;
import ru.Ignatiev.NauJava.domain.entity.ReportStatus;
import ru.Ignatiev.NauJava.domain.entity.UserEntity;
import ru.Ignatiev.NauJava.domain.repo.ReportRepository;
import ru.Ignatiev.NauJava.domain.repo.UserRepository;
import ru.Ignatiev.NauJava.domain.service.ReportService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;


    ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String getReportContext(Long id) {
        var found = reportRepository.findById(id);
        if (found.isPresent()) {
            return found.get().getReportContext();
        }
        else {
            log.error("Report with id= {} not found", id);;
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public String createReport(String reportContext) {
        ReportEntity report = new ReportEntity();
        report.setReportContext(reportContext);
        report.setReportStatus(ReportStatus.CREATED);
        reportRepository.save(report);
        return "Report successfully created with id " + report.getId();
    }

    @Override
    public CompletableFuture<Context> createAsyncAndFormContext() {
        return CompletableFuture.supplyAsync(() -> {
            long startTime = System.currentTimeMillis();
            AtomicLong userCount = new AtomicLong();
            AtomicReference<List<UserEntity>> userList = new AtomicReference<>();
            ReportEntity report = new ReportEntity();
            long totalTime = 0;
            AtomicLong userCountDuration = new AtomicLong();
            AtomicLong userListDuration = new AtomicLong();
            Thread userCountThread = new Thread(() -> {
                userCount.set(userRepository.count());
                userCountDuration.set(System.currentTimeMillis() - startTime);
                log.info("User count: {}",  userRepository.count());

            });

            Thread userListThread = new Thread(() -> {
                userList.set(userRepository.findAll());
                log.info("User list: {}",  userRepository.findAll());
                userListDuration.set(System.currentTimeMillis() - startTime);
            });
            try {
                userCountThread.start();
                userListThread.start();

                userCountThread.join();
                userListThread.join();

                totalTime = userCountDuration.get() + userListDuration.get();
                report.setReportStatus(ReportStatus.FINISHED);
                reportRepository.save(report);
                log.info("Report formed for {} ms.", totalTime);

            } catch (Exception e) {
                report.setReportStatus(ReportStatus.ERROR);
                reportRepository.save(report);
                log.error("Error when forming Report: {}", e.getMessage());
            }
            return setContextOfHtmlReport(userCount, userList, totalTime, userListDuration, userCountDuration);
        });
    }

    public Context setContextOfHtmlReport(AtomicLong userCount, AtomicReference<List<UserEntity>> userList,
                                          long totalTime, AtomicLong userListDuration, AtomicLong userCountDuration) {
        Context context = new Context();
        context.setVariable("userCount", userCount);
        context.setVariable("userList", userList.get());
        context.setVariable("totalTime", totalTime);
        context.setVariable("userListDuration", userListDuration.get());
        context.setVariable("userCountDuration", userCountDuration.get());
        return context;
    }
}
