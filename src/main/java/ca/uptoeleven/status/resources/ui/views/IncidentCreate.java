package ca.uptoeleven.status.resources.ui.views;

import ca.uptoeleven.status.resources.models.IncidentViewModel;
import io.dropwizard.views.View;
import lombok.Getter;

@Getter
public class IncidentCreate extends View {

    public IncidentCreate() {
        super("incident_create.ftl");
    }
}
