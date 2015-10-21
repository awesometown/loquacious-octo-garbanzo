package ca.uptoeleven.status.resources.ui.views;

import ca.uptoeleven.status.resources.models.IncidentViewModel;
import io.dropwizard.views.View;
import lombok.Getter;

@Getter
public class IncidentDetailsView extends View {
    private final IncidentViewModel incident;

    public IncidentDetailsView(IncidentViewModel incident) {
        super("incident.ftl");
        this.incident = incident;
    }
}
