package lark.monitor.job;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lark.database.mapper.MonitorJobMapper;
import lark.database.util.DatastoreFactory;
import lombok.extern.log4j.Log4j2;
import io.quarkus.scheduler.Scheduler;
import org.apache.ibatis.session.SqlSession;

@ApplicationScoped
@Log4j2
public class JobFactory {
    private final Scheduler scheduler;

    @Inject
    public JobFactory(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void init() {
        log.debug(scheduler.toString());
        try (SqlSession session = DatastoreFactory.openSession()) {
            MonitorJobMapper mapper = session.getMapper(MonitorJobMapper.class);
            log.info(mapper.selectAll());
        }
    }

}
