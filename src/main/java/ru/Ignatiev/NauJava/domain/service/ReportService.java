package ru.Ignatiev.NauJava.domain.service;


import org.thymeleaf.context.Context;

import java.util.concurrent.CompletableFuture;

public interface ReportService {

    String getReportContext(Long id);

    String createReport(String reportContext);

    CompletableFuture<Context> createAsyncAndFormContext();
}
