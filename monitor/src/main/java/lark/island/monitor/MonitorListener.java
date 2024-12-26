package lark.island.monitor;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lark.island.database.util.DatastoreFactory;
import lark.island.monitor.job.CronManager;
import lombok.extern.log4j.Log4j2;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Log4j2
public class MonitorListener {
    private final CronManager cronManager;

    @Inject
    public MonitorListener(CronManager cronManager) {
        this.cronManager = cronManager;
    }

    @ConfigProperty(name = "db.url")
    String jdbcUrl;

    @ConfigProperty(name = "db.user")
    String username;

    @ConfigProperty(name = "db.pw")
    String password;

    @ConfigProperty(name = "db.driver")
    String driverClassName;

    void onStart(@Observes StartupEvent ev) {
        log.info("Init App: {}", ev.toString());
        DatastoreFactory.init(jdbcUrl, username, password, driverClassName);
        cronManager.init();
    }
}
