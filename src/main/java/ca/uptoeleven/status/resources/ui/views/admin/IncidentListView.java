package ca.uptoeleven.status.resources.ui.views.admin;

import ca.uptoeleven.status.api.IncidentViewModel;
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
