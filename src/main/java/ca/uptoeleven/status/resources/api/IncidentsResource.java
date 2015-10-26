package ca.uptoeleven.status.resources.api;

import ca.uptoeleven.status.api.IncidentUpdateCreateModel;
import ca.uptoeleven.status.core.DefaultIncidentService;
import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.api.IncidentCreateModel;
import ca.uptoeleven.status.api.IncidentUpdateViewModel;
import ca.uptoeleven.status.api.IncidentViewModel;
import ca.uptoeleven.status.core.IncidentService;
import ca.uptoeleven.status.core.IncidentUpdate;
import com.google.inject.Inject;
import org.glassfish.jersey.server.Uri;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/incidents" )
@Produces(MediaType.APPLICATION_JSON)
public class IncidentsResource {

    private final IncidentService incidentService;

    @Context
    private UriInfo uriInfo;

    @Inject
    public IncidentsResource(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GET
    public List<IncidentViewModel> listIncidents() {
        return incidentService.getAllIncidents().stream().map(incident ->
                        map(incident)
        ).collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid IncidentCreateModel newIncident) {
        Incident created = incidentService.createIncident(newIncident);
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI userUri = ub.path(created.getId()).build();
        return Response.created(userUri).build();
    }

    @GET
    @Path("/{incidentId}" )
    public IncidentViewModel getIncident(@PathParam("incidentId" ) String incidentId) {
        Incident incident = incidentService.getIncident(incidentId);
        return map(incident);
    }

    @POST
    @Path("/{incidentId}/updates")
    public Response updateIncident(@PathParam("incidentId") String incidentId, @Valid IncidentUpdateCreateModel incidentUpdateCreateModel) {
        incidentService.updateIncident(incidentId, incidentUpdateCreateModel);
        return Response.ok().build();
    }

    private IncidentViewModel map(Incident incident) {
        List<IncidentUpdateViewModel> updates = incident.getIncidentUpdates().stream()
                .map(update -> new IncidentUpdateViewModel(
                        update.getId(),
                        update.getDescription(),
                        update.getNewState(),
                        update.getNewServiceStatusId(),
                        update.getCreatedAt(),
                        update.getUpdatedAt())).collect(Collectors.toList());

        //TODO: store the latest state on the incident itself
        String lastStatusId = incident.getIncidentUpdates().get(updates.size()-1).getNewServiceStatusId();

        IncidentViewModel vm = new IncidentViewModel(
                incident.getId(),
                incident.getTitle(),
                incident.getState(),
                lastStatusId,
                new ArrayList<>(incident.getAffectedServicesIds()),
                incident.getCreatedAt(),
                incident.getUpdatedAt(),
                updates
        );
        return vm;
    }
}
