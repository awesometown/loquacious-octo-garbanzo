package ca.uptoeleven.status.resources.ui.views.admin;

import ca.uptoeleven.status.core.IncidentState;
import ca.uptoeleven.status.core.ServiceStatus;
import ca.uptoeleven.status.resources.models.IncidentViewModel;
import com.google.common.collect.Lists;
import io.dropwizard.views.View;
import lombok.Getter;

import java.util.List;

@Getter
public class IncidentListView extends View {
    private final List<IncidentViewModel> incidents;

    public IncidentListView(List<IncidentViewModel> incidents) {
        super("incident_list.ftl");
        this.incidents = incidents;
    }
}
