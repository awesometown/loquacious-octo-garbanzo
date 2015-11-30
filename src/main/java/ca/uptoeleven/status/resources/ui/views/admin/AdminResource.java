package ca.uptoeleven.status.resources.ui.views.admin;

import ca.uptoeleven.status.resources.api.IncidentsResource;
import ca.uptoeleven.status.resources.api.ServicesResource;
import ca.uptoeleven.status.api.ServiceViewModel;
import io.dropwizard.views.View;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.*;
import java.util.List;

@Path("admin")
@Produces(MediaType.TEXT_HTML)
public class AdminResource {

	@Context
	private UriInfo uriInfo;

	@Context
	private HttpServletRequest request;

	private ServicesResource servicesResource;
	private IncidentsResource incidentsResource;

	public AdminResource(@Context ResourceContext rc) {
		this.servicesResource = rc.getResource(ServicesResource.class);
		this.incidentsResource = rc.getResource(IncidentsResource.class);
	}

	@GET
	public View adminDashboard() {
		return new AdminDashboardView(servicesResource.getServices().getData());
	}

	@GET
	@Path("/incidents")
	public View listIncidents() {
		return new IncidentListView(incidentsResource.listIncidents().getData());
	}

	@GET
	@Path("/incidents/{incidentId}")
	public View incidentDetails(@PathParam("incidentId") String incidentId) {
		return new IncidentUpdateView(incidentsResource.getIncident(incidentId), servicesResource.getServices().getData());
	}

	@GET
	@Path("/incidents/new")
	public View newIncident() {
		List<ServiceViewModel> services = servicesResource.getServices().getData();
		return new IncidentCreateView(services);
	}

	@GET
	@Path("/services")
	public View listServices() {
		List<ServiceViewModel> services = servicesResource.getServices().getData();
		return new ServiceListView(services);
	}

	@GET
	@Path("/services/new")
	public View newService() {
		return new ServiceCreateView();
	}
}
