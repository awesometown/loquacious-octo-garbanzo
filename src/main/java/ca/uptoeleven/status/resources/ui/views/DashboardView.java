package ca.uptoeleven.status.resources.ui.views;

import ca.uptoeleven.status.resources.models.ServiceViewModel;
import io.dropwizard.views.View;

import java.util.List;

public class DashboardView extends View {
    private final List<ServiceViewModel> services;

    public DashboardView(List<ServiceViewModel> services) {
        super("dashboard.ftl");
        this.services = services;
    }

    public List<ServiceViewModel> getServices() {
        return services;
    }
}