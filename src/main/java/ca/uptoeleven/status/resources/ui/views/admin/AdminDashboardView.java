package ca.uptoeleven.status.resources.ui.views.admin;

import ca.uptoeleven.status.api.ServiceViewModel;
import io.dropwizard.views.View;
import lombok.Getter;

import java.util.List;

@Getter
public class AdminDashboardView extends View {

    private List<ServiceViewModel> services;

    public AdminDashboardView(List<ServiceViewModel> services) {
        super("admin_dashboard.ftl");
        this.services = services;
    }
}
