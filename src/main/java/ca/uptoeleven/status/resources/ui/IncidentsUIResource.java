package ca.uptoeleven.status.resources.ui;

import ca.uptoeleven.status.resources.api.IncidentsResource;
import ca.uptoeleven.status.resources.api.ServicesResource;
import ca.uptoeleven.status.resources.models.IncidentCreateModel;
import ca.uptoeleven.status.resources.models.IncidentViewModel;
import ca.uptoeleven.status.resources.models.ServiceViewModel;
import ca.uptoeleven.status.resources.ui.views.IncidentCreateView;
import ca.uptoeleven.status.resources.ui.views.IncidentDetailsView;
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
    @Path("/new")
    public View newIncident(@PathParam("incidentId") String incidentId) {
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

    @GET
    @Path("/{incidentId}")
    public View viewIncident(@PathParam("incidentId") String incidentId) {
        IncidentsResource ir = rc.getResource(IncidentsResource.class);
        IncidentViewModel vm = ir.getIncident(incidentId);
        return new IncidentDetailsView(vm);
    }


}
