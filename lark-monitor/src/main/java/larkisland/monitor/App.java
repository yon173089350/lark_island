package larkisland.monitor;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.quartz.Scheduler;

@ApplicationScoped
@Log4j2
public class App {
    @Inject
    Scheduler quartz;

    void onStart(@Observes StartupEvent ev) {
        log.info(ev.toString());
        log.info(quartz.toString());
    }
}
