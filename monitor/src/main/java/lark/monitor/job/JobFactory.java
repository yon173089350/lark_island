package lark.monitor.job;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import io.quarkus.scheduler.Scheduler;

@ApplicationScoped
@Log4j2
public class JobFactory {
    private final Scheduler scheduler;

    @Inject
    public JobFactory(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void init() {
        log.info(scheduler.toString());
    }

}
