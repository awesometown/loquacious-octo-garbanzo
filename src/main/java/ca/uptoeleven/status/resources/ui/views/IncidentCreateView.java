package ca.uptoeleven.status.resources.ui.views;

import ca.uptoeleven.status.core.IncidentState;
import ca.uptoeleven.status.core.ServiceStatus;
import ca.uptoeleven.status.resources.models.ServiceViewModel;
import com.google.common.collect.Lists;
import io.dropwizard.views.View;
import lombok.Getter;

import java.util.List;

@Getter
public class IncidentCreateView extends View {

    private List<ServiceViewModel> services;

    public IncidentCreateView(List<ServiceViewModel> services) {
        super("incident_create.ftl");
        this.services = services;
    }

    public List<ServiceStatus> getStatuses() {
        return Lists.newArrayList(ServiceStatus.DEGRADED, ServiceStatus.MINOR, ServiceStatus.MAJOR);
    }

    public List<String> getStates() {
        return Lists.newArrayList(IncidentState.INVESTIGATING, IncidentState.IDENTIFIED, IncidentState.MONITORING, IncidentState.RESOLVED);
    }
}
