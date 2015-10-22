package ca.uptoeleven.status.resources.ui.views.dashboard;

import ca.uptoeleven.status.api.IncidentViewModel;
import ca.uptoeleven.status.api.ServiceViewModel;
import io.dropwizard.views.View;
import lombok.Getter;

import java.util.List;

@Getter
public class DashboardView extends View {
    private final List<ServiceViewModel> services;
    private final List<IncidentViewModel> incidents;

    public DashboardView(List<ServiceViewModel> services, List<IncidentViewModel> incidents) {
        super("dashboard.ftl");
        this.services = services;
        this.incidents = incidents;
    }

    public String classForStatus(String incidentStatus) {
        return "alert-success";
    }
}