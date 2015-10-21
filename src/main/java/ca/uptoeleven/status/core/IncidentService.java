package ca.uptoeleven.status.core;

import ca.uptoeleven.status.db.IncidentsRepository;
import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.db.models.IncidentUpdate;
import ca.uptoeleven.status.resources.models.IncidentCreateModel;
import com.google.inject.Inject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IncidentService {

    private final IncidentsRepository incidentsRepository;

    @Inject
    public IncidentService(IncidentsRepository incidentsRepository) {
        this.incidentsRepository = incidentsRepository;
    }

    public List<Incident> getAllIncidents() {
        return incidentsRepository.getAllIncidents();
    }

    public Incident getIncident(String incidentId) {
        return incidentsRepository.getIncident(incidentId);
    }

    public Incident createIncident(IncidentCreateModel model) {
        LocalDateTime now = LocalDateTime.now();
        Incident incident = new Incident(
                UUID.randomUUID().toString(),
                model.getTitle(),
                model.getState(),
                model.getAffectedServiceIds() == null ? new ArrayList<>() : model.getAffectedServiceIds(),
                model.getStartTime() == null ? now : model.getStartTime(),
                now,
                now);
        IncidentUpdate initialUpdate = new IncidentUpdate(
                UUID.randomUUID().toString(),
                incident.getId(),
                model.getDescription(),
                model.getState(),
                model.getServiceStatusId(),
                now,
                now);

        Incident created = incidentsRepository.createIncident(incident, initialUpdate);
        return created;
    }

}
