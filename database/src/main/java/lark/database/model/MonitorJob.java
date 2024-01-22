package lark.database.model;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonitorJob {
    private String jobId;

    private String countryRegionCode;

    private String cronJson;

    private String oncePerDuration;

    private OffsetDateTime holdStartTime;

    private OffsetDateTime holdEndTime;

    private OffsetDateTime lastTrigTime;
}