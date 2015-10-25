package ca.uptoeleven.status.resources.ui.views.admin;

import ca.uptoeleven.status.api.ServiceViewModel;
import io.dropwizard.views.View;

import java.util.List;

public class ServiceCreateView extends View {

    public ServiceCreateView() {
        super("service_create.ftl");
    }
}
