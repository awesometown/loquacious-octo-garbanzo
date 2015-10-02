package ca.uptoeleven.status.resources.api;

import ca.uptoeleven.status.core.IncidentService;
import ca.uptoeleven.status.db.IncidentsDAO;
import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.resources.models.IncidentCreateModel;
import ca.uptoeleven.status.resources.models.IncidentViewModel;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/api/incidents")
@Produces(MediaType.APPLICATION_JSON)
public class IncidentsResource {

    private final IncidentService incidentService;

    @Inject
    public IncidentsResource(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GET
    public List<IncidentViewModel> listIncidents() {
        return new ArrayList<>();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(IncidentCreateModel newIncident) {
        Incident created = incidentService.createIncident(newIncident);
        return Response.ok(created).build();
    }


    @GET
    @Path("test")
    public Response getTest() {
        List<Incident> incidents = incidentService.getAllIncidents();
        return Response.ok(incidents).build();
    }
}
