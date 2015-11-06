package ca.uptoeleven.status.resources.ui.views.dashboard;

import ca.uptoeleven.status.resources.api.IncidentsResource;
import ca.uptoeleven.status.resources.api.ServicesResource;
import ca.uptoeleven.status.api.IncidentViewModel;
import ca.uptoeleven.status.api.ServiceViewModel;
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

	private final IncidentsResource incidentsResource;
	private final ServicesResource servicesResource;

	public DashboardResource(@Context ResourceContext rc) {
		incidentsResource = rc.getResource(IncidentsResource.class);
		servicesResource = rc.getResource(ServicesResource.class);
	}

    @GET
    public View getDashboard() {
		List<ServiceViewModel> svms = servicesResource.getServices().getData();

        List<IncidentViewModel> incidents = incidentsResource.listIncidents().getData();
        return new DashboardView(svms, incidents);
    }

    @GET
    @Path("/incidents/{incidentId}")
    public View viewIncident(@PathParam("incidentId") String incidentId) {
        return new IncidentDetailsView(
				incidentsResource.getIncident(incidentId),
				servicesResource.getServices().getData()
		);
    }
}
