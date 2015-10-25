package ca.uptoeleven.status.core;

import ca.uptoeleven.status.api.IncidentUpdateCreateModel;
import ca.uptoeleven.status.db.IncidentsRepository;
import ca.uptoeleven.status.api.IncidentCreateModel;
import com.google.inject.Inject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ca.uptoeleven.status.core.IdGenerator.newId;
import static ca.uptoeleven.status.core.UtcDateTime.nowUtc;

public class DefaultIncidentService implements IncidentService {

    private final IncidentsRepository incidentsRepository;

    @Inject
    public DefaultIncidentService(IncidentsRepository incidentsRepository) {
        this.incidentsRepository = incidentsRepository;
    }

    @Override
    public List<Incident> getAllIncidents() {
        return incidentsRepository.getAllIncidents();
    }

    @Override
    public Incident getIncident(String incidentId) {
        return incidentsRepository.getIncident(incidentId);
    }

    @Override
    public Incident createIncident(IncidentCreateModel model) {
        Incident incident = Incident.newIncident(model.getTitle(), model.getDescription(), model.getState(), model.getServiceStatusId(), model.getAffectedServiceIds());
        return incidentsRepository.create(incident);
    }

    @Override
    public void updateIncident(String incidentId, IncidentUpdateCreateModel model) {
        LocalDateTime now = nowUtc();
        IncidentUpdate update = map(model, now);
        Incident incident = getIncident(incidentId);
        incident = incident
                .withUpdatedAt(now)
                .withAdditionalUpdate(update);
        incidentsRepository.save(incident);
    }

    private Incident map(IncidentCreateModel model, LocalDateTime now) {
        return new Incident(
                    newId(),
                    model.getTitle(),
                    model.getState(),
                    model.getAffectedServiceIds() == null ? new ArrayList<>() : model.getAffectedServiceIds(),
                    model.getStartTime() == null ? now : model.getStartTime(),
                    now,
                    now,
                    null);
    }

    private IncidentUpdate map(IncidentUpdateCreateModel model, LocalDateTime now) {
        return new IncidentUpdate(
                newId(),
                model.getDescription(),
                model.getState(),
                model.getServiceStatusId(),
                now,
                now
        );
    }
}
