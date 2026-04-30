package ru.Ignatiev.NauJava.domain.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import ru.Ignatiev.NauJava.domain.entity.ReportEntity;
import ru.Ignatiev.NauJava.domain.entity.ReportStatus;
import ru.Ignatiev.NauJava.domain.entity.UserEntity;
import ru.Ignatiev.NauJava.domain.repo.ReportRepository;
import ru.Ignatiev.NauJava.domain.repo.UserRepository;
import ru.Ignatiev.NauJava.domain.service.ReportService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    public ReportEntity getReport(Long id) {
        var report = reportRepository.findById(id);
        if (report.get().getReportStatus() == ReportStatus.FINISHED) {
            log.info("REPORT with id ={} was sent TO HTML Report", id);
            return report.get();
        }
        else if (report.get().getReportStatus() == ReportStatus.ERROR) {
            log.error("Report with id={} dropped error", id);
        }
        else {
            log.info("Report with id={} is not formed yet", id);
        }
        return null;
    }

    @Override
    public Long createAsyncReport() {
        ReportEntity reportPlaceholder = new ReportEntity();
        reportPlaceholder.setReportStatus(ReportStatus.CREATED);
        reportPlaceholder = reportRepository.save(reportPlaceholder);
        Long reportId = reportPlaceholder.getId();


        CompletableFuture.runAsync(() -> {
            try {
                long startTime = System.currentTimeMillis();

                AtomicLong userCount = new AtomicLong();
                AtomicReference<List<UserEntity>> userList = new AtomicReference<>();
                AtomicLong userCountDuration = new AtomicLong();
                AtomicLong userListDuration = new AtomicLong();

                Thread userCountThread = new Thread(() -> {
                    userCount.set(userRepository.count());
                    userCountDuration.set(System.currentTimeMillis() - startTime);
                });

                Thread userListThread = new Thread(() -> {
                    userList.set(userRepository.findAll());
                    userListDuration.set(System.currentTimeMillis() - startTime);
                });

                userCountThread.start();
                userListThread.start();
                userCountThread.join();
                userListThread.join();

                long totalTime = System.currentTimeMillis() - startTime;

                reportRepository.findById(reportId).ifPresent(report -> {
                    report.setUserCount(userCount.get()); // Извлекаем long из AtomicLong
                    report.setTotalTime(totalTime);
                    report.setUserCountDuration(userCountDuration.get());
                    report.setUserListDuration(userListDuration.get());

                    List<String> names = userList.get().stream()
                            .map(UserEntity::getUsername)
                            .collect(Collectors.toList());
                    report.setUserListNames(names);

                    report.setReportStatus(ReportStatus.FINISHED);
                    reportRepository.save(report);
                });

                log.info("Report {} formed for {} ms.", reportId, totalTime);

            } catch (Exception e) {
                log.error("Error when forming Report {}: {}", reportId, e.getMessage());
                reportRepository.findById(reportId).ifPresent(report -> {
                    report.setReportStatus(ReportStatus.ERROR);
                    reportRepository.save(report);
                });
            }
        });

        return reportId;
    }
}
