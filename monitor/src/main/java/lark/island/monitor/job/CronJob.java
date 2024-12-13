package lark.island.monitor.job;

import lark.island.database.mapper.MonitorJobMapper;
import lark.island.database.model.MonitorJob;
import lark.island.database.util.DatastoreFactory;
import lark.island.enums.TimeZoneEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSession;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Log4j2
public class CronJob {
    private final String jobId;
    private final String countryRegionCode;

    public CronJob(String jobId, String countryRegionCode) {
        this.jobId = jobId;
        this.countryRegionCode = countryRegionCode;
    }

    public void run() {
        try (SqlSession sqlSession = DatastoreFactory.openSession()) {
            MonitorJobMapper mapper = sqlSession.getMapper(MonitorJobMapper.class);
            MonitorJob monitorJob = mapper.selectByPrimaryKeySkipLocked(jobId, countryRegionCode);

            if (monitorJob == null) {
                log.info("Record not found for Job[{}-{}], might being scheduled by other instance...", jobId, countryRegionCode);
                return;
            }

            ZonedDateTime now = ZonedDateTime.now();
            ZoneId zoneId = Objects.requireNonNull(TimeZoneEnum.getZoneId(monitorJob.getCountryRegionCode()));
            String oncePerDuration = monitorJob.getOncePerDuration();

            if (Objects.nonNull(monitorJob.getLastTrigTime())
                    && monitorJob.getLastTrigTime().atZoneSameInstant(zoneId)
                    .isAfter(now.withZoneSameInstant(zoneId).truncatedTo(ChronoUnit.valueOf(oncePerDuration)))) {
                log.info("Job[{}-{}] has been called successfully in {}", jobId, countryRegionCode, oncePerDuration);
                return;
            }

            monitorJob.setLastTrigTime(now.toOffsetDateTime());
            mapper.updateByPrimaryKey(monitorJob);
            sqlSession.commit();
        }
    }
}
