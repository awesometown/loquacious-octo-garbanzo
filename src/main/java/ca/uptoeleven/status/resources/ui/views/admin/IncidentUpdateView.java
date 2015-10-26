package ca.uptoeleven.status.resources.ui.views.admin;

import ca.uptoeleven.status.core.IncidentState;
import ca.uptoeleven.status.core.ServiceStatus;
import ca.uptoeleven.status.api.IncidentViewModel;
import com.google.common.collect.Lists;
import io.dropwizard.views.View;
import lombok.Getter;

import java.util.List;

@Getter
public class IncidentUpdateView extends View {
    private final IncidentViewModel incident;

    public IncidentUpdateView(IncidentViewModel incident) {
        super("incident_update.ftl");
        this.incident = incident;
    }

    public List<ServiceStatus> getStatuses() {
        return Lists.newArrayList(ServiceStatus.OK, ServiceStatus.DEGRADED, ServiceStatus.MINOR, ServiceStatus.MAJOR);
    }

    public List<String> getStates() {
        return Lists.newArrayList(IncidentState.INVESTIGATING, IncidentState.IDENTIFIED, IncidentState.MONITORING, IncidentState.RESOLVED);
    }
}
