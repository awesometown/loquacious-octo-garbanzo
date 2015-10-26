package ca.uptoeleven.status;

import ca.uptoeleven.status.core.DefaultIncidentService;
import ca.uptoeleven.status.core.IncidentService;
import com.google.inject.AbstractModule;

public class StatusModule extends AbstractModule {
    @Override
    protected void configure() {
		bind(IncidentService.class).to(DefaultIncidentService.class);
    }
}
