package ca.uptoeleven.status.core;

import ca.uptoeleven.status.api.IncidentCreateModel;
import ca.uptoeleven.status.api.IncidentUpdateCreateModel;

import java.util.List;

public interface IncidentService {
    List<Incident> getAllIncidents();

    Incident getIncident(String incidentId);

    Incident createIncident(IncidentCreateModel model);

    Incident updateIncident(String incidentId, IncidentUpdateCreateModel model);
}
