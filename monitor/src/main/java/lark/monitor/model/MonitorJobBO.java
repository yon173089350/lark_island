package lark.monitor.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZoneId;
import java.util.List;

@Data
@Builder
public class MonitorJobBO{
    private String jobId;
    private String countryRegionCode;
    private List<String> cronList;
    private ZoneId zoneId;
}
