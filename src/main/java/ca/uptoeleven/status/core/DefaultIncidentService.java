package ca.uptoeleven.status.core;

import ca.uptoeleven.status.api.IncidentCreateModel;
import ca.uptoeleven.status.api.IncidentUpdateCreateModel;
import ca.uptoeleven.status.db.IncidentsRepository;
import ca.uptoeleven.status.db.ServicesDAO;
import com.google.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

import static ca.uptoeleven.status.core.IdGenerator.newId;
import static ca.uptoeleven.status.core.UtcDateTime.nowUtc;

public class DefaultIncidentService implements IncidentService {

	private final IncidentsRepository incidentsRepository;

	private final ServicesDAO servicesDAO;

	@Inject
	public DefaultIncidentService(IncidentsRepository incidentsRepository, ServicesDAO servicesDAO) {
		this.incidentsRepository = incidentsRepository;
		this.servicesDAO = servicesDAO;
	}

	@Override
	public List<Incident> getAllIncidents() {
		return incidentsRepository.getAllIncidents();
	}

	@Override
	public List<Incident> getAllIncidentsByType(final String type) {
		return this.incidentsRepository.getAllIncidentsByType(type);
	}

	@Override
	public List<Incident> getActiveIncidents() {
		return incidentsRepository.getActiveIncidents();
	}

	@Override
	public List<Incident> getActiveIncidentsByType(final String type) {
		return incidentsRepository.getActiveIncidentsByType(type);
	}

	@Override
	public Incident getIncident(String incidentId) {
		return incidentsRepository.getIncident(incidentId);
	}

	@Override
	public Incident createIncident(final IncidentCreateModel model) {

		final Incident incident = Incident.newIncident(model.getTitle(), model.getDescription(), model.getState(),
				model.getType(), model.getServiceStatusId(), model.getAffectedServiceIds());

		final Incident created = this.incidentsRepository.create(incident);

		created.getAffectedServicesIds().forEach(id ->
				this.servicesDAO.updateServiceStatusId(id, model.getServiceStatusId()));

		return created;
	}

	@Override
	public Incident updateIncident(String incidentId, IncidentUpdateCreateModel model) {
		LocalDateTime now = nowUtc();
		IncidentUpdate update = map(model, now);
		Incident incident = getIncident(incidentId);
		incident = incident
				.withState(model.getState())
				.withUpdatedAt(now)
				.withAdditionalUpdate(update);
		Incident saved = incidentsRepository.save(incident);
		for (String serviceId : saved.getAffectedServicesIds()) {
			servicesDAO.updateServiceStatusId(serviceId, model.getServiceStatusId());
		}
		return saved;
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
