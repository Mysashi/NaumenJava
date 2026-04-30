package ru.Ignatiev.NauJava.domain.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.Ignatiev.NauJava.domain.entity.ReportEntity;
import ru.Ignatiev.NauJava.domain.entity.ReportStatus;
import ru.Ignatiev.NauJava.domain.entity.UserEntity;
import ru.Ignatiev.NauJava.domain.repo.ReportRepository;
import ru.Ignatiev.NauJava.domain.repo.UserRepository;
import ru.Ignatiev.NauJava.domain.service.ReportService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final Executor reportTaskExecutor;


    ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, Executor reportTaskExecutor) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.reportTaskExecutor = reportTaskExecutor;
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
        ReportEntity savedReport = reportRepository.save(reportPlaceholder);
        Long reportId = savedReport.getId();
        CompletableFuture.runAsync(() -> processReport(reportId), reportTaskExecutor);

        return reportId;
    }

    private void processReport(Long reportId) {
        try {
            long globalStart = System.currentTimeMillis();

            CompletableFuture<Long> countFuture = CompletableFuture.supplyAsync(() -> {
                long count = userRepository.count();
                return count;
            }, reportTaskExecutor);

            CompletableFuture<List<UserEntity>> listFuture = CompletableFuture.supplyAsync(
                    userRepository::findAll, reportTaskExecutor);

            long countStart = System.currentTimeMillis();
            Long count = countFuture.join();
            long countDuration = System.currentTimeMillis() - countStart;

            long listStart = System.currentTimeMillis();
            List<UserEntity> users = listFuture.join();
            long listDuration = System.currentTimeMillis() - listStart;

            long totalTime = System.currentTimeMillis() - globalStart;

            reportRepository.findById(reportId).ifPresent(report -> {
                report.setUserCount(count);
                report.setUserCountDuration(countDuration);
                report.setUserListDuration(listDuration);
                report.setTotalTime(totalTime);
                report.setUserListNames(users.stream()
                        .map(UserEntity::getUsername)
                        .collect(Collectors.toList()));
                report.setReportStatus(ReportStatus.FINISHED);
                reportRepository.save(report);
            });

        } catch (Exception e) {
            log.error("Error forming report {}", reportId, e);
            updateReportError(reportId);
        }

    }

    private void updateReportError(Long id) {
        reportRepository.findById(id).ifPresent(report -> {
            report.setReportStatus(ReportStatus.ERROR);
            reportRepository.save(report);
        });
    }
}
