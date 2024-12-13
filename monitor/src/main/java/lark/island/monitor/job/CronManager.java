package lark.island.monitor.job;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.quarkus.scheduler.Scheduler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lark.island.database.mapper.MonitorJobMapper;
import lark.island.database.model.MonitorJob;
import lark.island.database.util.DatastoreFactory;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

@ApplicationScoped
@Log4j2
public class CronManager {
    private final Scheduler scheduler;

    private final Gson gson = new Gson();

    @Inject
    public CronManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void init() {
        initScheduler();
    }

    private void initScheduler() {
        try (SqlSession session = DatastoreFactory.openSession()) {
            MonitorJobMapper mapper = session.getMapper(MonitorJobMapper.class);
            mapper.selectAll().forEach(this::add2Scheduler);
        }
    }

    private List<String> convertJson2CronList(String json) {
        return gson.fromJson(json, new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    private void add2Scheduler(MonitorJob job) {
        List<String> list = convertJson2CronList(job.getCronJson());
        for (int i = 0; i < list.size(); i++) {
            scheduler.newJob(String.join("_", job.getJobId(), job.getCountryRegionCode(), String.valueOf(i)))
                    .setCron(list.get(i))
                    .setTask(_ -> new CronJob(job.getJobId(), job.getCountryRegionCode()).run())
                    .schedule();
        }
        log.info(scheduler.getScheduledJobs());
    }
}
