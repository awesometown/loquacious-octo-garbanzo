package ca.uptoeleven.status.resources;

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

@Path("/api/services/{serviceid}/incidents")
@Produces(MediaType.APPLICATION_JSON)
public class IncidentsResource {

    private final IncidentsDAO incidentsDAO;

    @Inject
    public IncidentsResource(IncidentsDAO incidentsDAO) {
        this.incidentsDAO = incidentsDAO;
    }

    @GET
    public List<IncidentViewModel> listIncidents() {
        return new ArrayList<>();
    }

    @POST
    public Response create(IncidentCreateModel newIncident) {
        return Response.created(null).build();
    }

    @POST
    @Path("test/{foo}")
    public Response test(@PathParam("foo") String foo) {
        //incidentsDAO.insert(UUID.randomUUID().toString(), foo);
        return Response.ok().build();
    }

    @GET
    @Path("test")
    public Response getTest() {
        List<Incident> incidents = incidentsDAO.findAllIncidents();
        return Response.ok(incidents).build();
    }
}
