package ca.uptoeleven.status.core;

import ca.uptoeleven.status.api.IncidentCreateModel;
import ca.uptoeleven.status.api.IncidentUpdateCreateModel;

import java.util.List;

public interface IncidentService {

	List<Incident> getAllIncidents();

	List<Incident> getAllIncidentsByType(final String type);

	List<Incident> getActiveIncidents();

	List<Incident> getActiveIncidentsByType(final String type);

	Incident getIncident(final String incidentId);

	Incident createIncident(final IncidentCreateModel model);

	Incident updateIncident(final String incidentId, final IncidentUpdateCreateModel model);
}
