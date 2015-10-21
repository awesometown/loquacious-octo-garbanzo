package ca.uptoeleven.status.resources.ui.views.admin;

import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.resources.api.IncidentsResource;
import ca.uptoeleven.status.resources.api.ServicesResource;
import ca.uptoeleven.status.resources.models.IncidentCreateModel;
import ca.uptoeleven.status.resources.models.ServiceViewModel;
import ca.uptoeleven.status.resources.ui.views.admin.IncidentCreateView;
import io.dropwizard.views.View;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("admin/incidents")
@Produces(MediaType.TEXT_HTML)
public class IncidentsUIResource {

    @Context
    private ResourceContext rc;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpServletRequest request;

    @GET
    @Path("/{incidentId}")
    public View incidentDetails(@PathParam("incidentId") String incidentId) {
        IncidentsResource ir = rc.getResource(IncidentsResource.class);
        return new IncidentUpdateView(ir.getIncident(incidentId));
    }

    @GET
    @Path("/new")
    public View newIncident() {
        ServicesResource sr = rc.getResource(ServicesResource.class);
        List<ServiceViewModel> services = sr.getServices();
        return new IncidentCreateView(services);
    }

    @POST
    @Path("/new")
    @Consumes("application/x-www-form-urlencoded")
    public Response createIncident(
            @FormParam("incident-title") String incidentTitle,
            @FormParam("incident-update-description") String updateDescription,
            @FormParam("incident-state") String incidentStateId,
            @FormParam("new-service-status") String serviceStatusId) {
        IncidentCreateModel newIncident = new IncidentCreateModel(incidentTitle, updateDescription, incidentStateId, serviceStatusId, new ArrayList<String>(), LocalDateTime.now());
        IncidentsResource ir = rc.getResource(IncidentsResource.class);
        return ir.create(newIncident);
    }
}
