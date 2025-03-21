package lark.island.monitor;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lark.island.database.util.DatastoreFactory;
import lark.island.monitor.job.CronManager;
import lombok.extern.log4j.Log4j2;

@ApplicationScoped
@Log4j2
public class MonitorListener {
    private final CronManager cronManager;

    @Inject
    public MonitorListener(CronManager cronManager) {
        this.cronManager = cronManager;
    }

    void onStart(@Observes StartupEvent ev) {
        log.info("Init App: {}", ev.toString());
        DatastoreFactory.init();
        cronManager.init();
    }
}
