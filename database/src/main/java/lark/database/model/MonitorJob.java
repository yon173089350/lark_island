package lark.database.model;

import lombok.Data;

@Data
public class MonitorJob {
    private String jobId;

    private String cron;
}