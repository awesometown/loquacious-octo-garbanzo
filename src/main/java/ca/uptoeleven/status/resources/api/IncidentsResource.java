package ca.uptoeleven.status.resources.api;

import ca.uptoeleven.status.api.*;
import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.core.IncidentService;
import com.google.common.base.Strings;
import com.google.inject.Inject;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
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
	public ListHolder<IncidentViewModel> listIncidents(@QueryParam("type") final String queryType) {

		final List<Incident> incidents = Strings.isNullOrEmpty(queryType) ?
				this.incidentService.getAllIncidents() :
				this.incidentService.getAllIncidentsByType(queryType);

		return new ListHolder<>(incidents.stream().map(this::map).collect(Collectors.toList()));
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
	public ListHolder<IncidentViewModel> getActiveIncidents(@QueryParam("type") final String queryType) {

		final List<Incident> incidents = Strings.isNullOrEmpty(queryType) ?
			this.incidentService.getActiveIncidents() :
			this.incidentService.getActiveIncidentsByType(queryType);

		return new ListHolder<>(incidents.stream().map(this::map).collect(Collectors.toList()));
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
