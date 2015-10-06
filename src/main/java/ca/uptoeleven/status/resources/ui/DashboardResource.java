package ca.uptoeleven.status.resources.ui;

import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.resources.api.IncidentsResource;
import ca.uptoeleven.status.resources.api.ServicesResource;
import ca.uptoeleven.status.resources.models.IncidentViewModel;
import ca.uptoeleven.status.resources.models.ServiceViewModel;
import ca.uptoeleven.status.resources.ui.views.DashboardView;
import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("dashboard")
@Produces(MediaType.TEXT_HTML)
public class DashboardResource {

    @Context
    private ResourceContext rc;

    @GET
    public View getDashboard() {
        ServicesResource sr = rc.getResource(ServicesResource.class);
        List<ServiceViewModel> svms = sr.getServices();

        IncidentsResource ir = rc.getResource(IncidentsResource.class);
        List<IncidentViewModel> incidents = ir.listIncidents();
        return new DashboardView(svms, incidents);
    }
}
