package lark.database.model;

import java.util.Date;
import lombok.Data;

@Data
public class MonitorJob {
    private String jobId;

    private String cron;

    private Date triggerTime;
}