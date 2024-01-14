package lark.database.model;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonitorJob {
    private String jobId;

    private String countryRegionCode;

    private String cronJson;

    private String oncePerDuration;

    private Date holdStartTime;

    private Date holdEndTime;

    private Date lastTrigTime;
}