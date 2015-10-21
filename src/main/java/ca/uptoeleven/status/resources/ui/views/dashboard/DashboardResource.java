package ca.uptoeleven.status.resources.ui.views.dashboard;

import ca.uptoeleven.status.resources.api.IncidentsResource;
import ca.uptoeleven.status.resources.api.ServicesResource;
import ca.uptoeleven.status.resources.models.IncidentViewModel;
import ca.uptoeleven.status.resources.models.ServiceViewModel;
import ca.uptoeleven.status.resources.ui.views.dashboard.DashboardView;
import ca.uptoeleven.status.resources.ui.views.dashboard.IncidentDetailsView;
import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    @GET
    @Path("/{incidentId}")
    public View viewIncident(@PathParam("incidentId") String incidentId) {
        IncidentsResource ir = rc.getResource(IncidentsResource.class);
        IncidentViewModel vm = ir.getIncident(incidentId);
        return new IncidentDetailsView(vm);
    }
}
