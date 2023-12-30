package lark.monitor;

import io.quarkus.runtime.StartupEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lark.monitor.job.JobFactory;
import lombok.extern.log4j.Log4j2;

@ApplicationScoped
@Log4j2
public class App {
    private final JobFactory jobFactory;

    @Inject
    public App(JobFactory jobFactory) {
        this.jobFactory = jobFactory;
    }

    void onStart(@Observes StartupEvent ev) {
        log.info("Init App: {}", ev.toString());
        jobFactory.init();
    }
}
