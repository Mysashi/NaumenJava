package ru.Ignatiev.NauJava.domain.service;


import ru.Ignatiev.NauJava.domain.entity.ReportEntity;

public interface ReportService {

    ReportEntity getReport(Long id);

    Long createAsyncReport();
}
