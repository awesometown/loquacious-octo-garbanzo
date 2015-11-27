package ca.uptoeleven.status.resources.api;

import ca.uptoeleven.status.api.*;
import ca.uptoeleven.status.core.DefaultIncidentService;
import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.core.IncidentService;
import ca.uptoeleven.status.core.IncidentUpdate;
import com.google.inject.Inject;
import org.glassfish.jersey.server.Uri;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ca.uptoeleven.status.core.UtcDateTime.asUtc;

@Path("/incidents")
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
	public ListHolder<IncidentViewModel> listIncidents() {
		return new ListHolder<>(
				incidentService.getAllIncidents().stream().map(incident ->
								map(incident)
				).collect(Collectors.toList()));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("ADMIN")
	public Response create(@Valid IncidentCreateModel newIncident) {
		Incident created = incidentService.createIncident(newIncident);
		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI userUri = ub.path(created.getId()).build();
		return Response.created(userUri).build();
	}

	@GET
	@Path("/active")
	public ListHolder<IncidentViewModel> getActiveIncidents() {
		return new ListHolder<>(
				incidentService.getActiveIncidents().stream().map(incident ->
								map(incident)
				).collect(Collectors.toList()));
	}

	@GET
	@Path("/{incidentId}")
	public IncidentViewModel getIncident(@PathParam("incidentId") String incidentId) {
		Incident incident = incidentService.getIncident(incidentId);
		return map(incident);
	}

	@POST
	@Path("/{incidentId}")
	@RolesAllowed("ADMIN")
	public IncidentViewModel updateIncident(@PathParam("incidentId") String incidentId, @Valid IncidentUpdateCreateModel incidentUpdateCreateModel) {
		Incident updated = incidentService.updateIncident(incidentId, incidentUpdateCreateModel);
		return map(updated);
	}

	private IncidentViewModel map(Incident incident) {
		List<IncidentUpdateViewModel> updates = incident.getIncidentUpdates().stream()
				.map(update -> new IncidentUpdateViewModel(
						update.getId(),
						update.getDescription(),
						update.getNewState(),
						update.getNewServiceStatusId(),
						asUtc(update.getCreatedAt()),
						asUtc(update.getUpdatedAt()))).collect(Collectors.toList());

		//TODO: store the latest state on the incident itself
		String lastStatusId = incident.getIncidentUpdates().get(updates.size() - 1).getNewServiceStatusId();

		IncidentViewModel vm = new IncidentViewModel(
				incident.getId(),
				incident.getTitle(),
				incident.getState(),
				lastStatusId,
				new ArrayList<>(incident.getAffectedServicesIds()),
				asUtc(incident.getCreatedAt()),
				asUtc(incident.getUpdatedAt()),
				updates
		);
		return vm;
	}
}
