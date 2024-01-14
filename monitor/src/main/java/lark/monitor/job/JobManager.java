package lark.monitor.job;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.quarkus.scheduler.Scheduler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lark.database.mapper.MonitorJobMapper;
import lark.database.model.MonitorJob;
import lark.database.util.DatastoreFactory;
import lark.monitor.model.MonitorJobBO;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSession;
import org.quartz.SchedulerException;

import java.time.ZoneId;
import java.util.*;

@ApplicationScoped
@Log4j2
public class JobManager {
    private final Scheduler scheduler;

    private final Gson gson = new Gson();

    private final Map<String, MonitorJobBO> jobMap = new HashMap<>();

    @Inject
    public JobManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void init() {

        initScheduler();

    }

    private void initScheduler() {
        try (SqlSession session = DatastoreFactory.openSession()) {
            MonitorJobMapper mapper = session.getMapper(MonitorJobMapper.class);
            for (MonitorJob monitorJob : mapper.selectAll()) {
                MonitorJobBO job = MonitorJobBO.builder().jobId(monitorJob.getJobId())
                        .countryRegionCode(monitorJob.getCountryRegionCode())
                        .cronList(convertJson2CronList(monitorJob.getCronJson()))
                        .zoneId(ZoneId.systemDefault())
                        .build();
                jobMap.put(job.getJobId(), job);
                add2Scheduler(job);
            }
        }
    }

    private List<String> convertJson2CronList(String json) {
        return gson.fromJson(json, new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    private void add2Scheduler(MonitorJobBO job) {
        job.getCronList().forEach(cron -> {
            scheduler.newJob(job.getJobId())
                    .setCron(cron)
                    .setTask(executionContext -> {
                        log.info("Cron: {}, {}", cron,new Date());
                    })
                    .schedule();
        });
        log.info(scheduler.getScheduledJobs());

    }


}
