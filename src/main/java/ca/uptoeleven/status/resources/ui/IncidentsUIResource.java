package ca.uptoeleven.status.resources.ui;

import ca.uptoeleven.status.resources.api.IncidentsResource;
import ca.uptoeleven.status.resources.models.IncidentViewModel;
import ca.uptoeleven.status.resources.ui.views.DashboardView;
import ca.uptoeleven.status.resources.ui.views.IncidentCreate;
import ca.uptoeleven.status.resources.ui.views.IncidentView;
import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("admin/incidents")
@Produces(MediaType.TEXT_HTML)
public class IncidentsUIResource {

    @Context
    private ResourceContext rc;

    @GET
    @Path("/new")
    public View createIncident(@PathParam("incidentId") String incidentId) {
        return new IncidentCreate();
    }

    @GET
    @Path("/{incidentId}")
    public View viewIncident(@PathParam("incidentId") String incidentId) {
        IncidentsResource ir = rc.getResource(IncidentsResource.class);
        IncidentViewModel vm = ir.getIncident(incidentId);
        return new IncidentView(vm);
    }
}
