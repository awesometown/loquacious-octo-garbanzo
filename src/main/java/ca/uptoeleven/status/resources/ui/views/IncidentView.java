package ca.uptoeleven.status.resources.ui.views;

import ca.uptoeleven.status.resources.models.IncidentViewModel;
import ca.uptoeleven.status.resources.models.ServiceViewModel;
import io.dropwizard.views.View;
import lombok.Getter;

import java.util.List;

@Getter
public class IncidentView extends View {
    private final IncidentViewModel incident;

    public IncidentView(IncidentViewModel incident) {
        super("incident.ftl");
        this.incident = incident;
    }
}
