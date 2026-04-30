package ru.Ignatiev.NauJava.domain.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Entity
@Table(name="report")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_status")
    private ReportStatus reportStatus;

    @Column(name = "report_context")
    private String reportContext;

    @Column(name="user_count")
    private Long userCount;

    @Column(name="total_time")
    private Long totalTime;

    @Column(name="user_count_duration")
    private Long userCountDuration;

    @Column(name="user_list_duration")
    private Long userListDuration;

    @ElementCollection
    @CollectionTable(name = "report_user_names", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "user_name")
    private List<String> userListNames;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getReportContext() {
        return reportContext;
    }

    public void setReportContext(String reportContext) {
        this.reportContext = reportContext;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public Long getUserCountDuration() {
        return userCountDuration;
    }

    public void setUserCountDuration(Long userCountDuration) {
        this.userCountDuration = userCountDuration;
    }

    public Long getUserListDuration() {
        return userListDuration;
    }

    public void setUserListDuration(Long userListDuration) {
        this.userListDuration = userListDuration;
    }

    public List<String> getUserListNames() {
        return userListNames;
    }

    public void setUserListNames(List<String> userListNames) {
        this.userListNames = userListNames;
    }
}
