package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.core.IncidentUpdate;
import org.skife.jdbi.v2.DBI;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class IncidentsRepository {

	private final DBI dbi;

	private final IncidentsDAO incidentsDAO;

	private final IncidentUpdatesDAO updatesDAO;

	private final ServicesDAO servicesDAO;

	@Inject
	public IncidentsRepository(DBI dbi, IncidentsDAO incidentsDAO, IncidentUpdatesDAO updatesDAO, ServicesDAO servicesDAO) {
		this.dbi = dbi;
		this.incidentsDAO = incidentsDAO;
		this.updatesDAO = updatesDAO;
		this.servicesDAO = servicesDAO;
	}

	public List<Incident> getAllIncidents() {
		List<Incident> allIncidents = incidentsDAO.findAllIncidents();
		return allIncidents.stream().map(incident -> populateUpdates(incident)).collect(Collectors.toList());
	}

	public List<Incident> getAllIncidentsByType(final String type) {
		return this.incidentsDAO.findAllIncidentsByType(type).stream().map(this::populateUpdates).collect(Collectors.toList());
	}

	public List<Incident> getActiveIncidents() {
		List<Incident> activeIncidents = incidentsDAO.findActiveIncidents();
		return activeIncidents.stream().map(incident -> populateUpdates(incident)).collect(Collectors.toList());
	}

	public List<Incident> getActiveIncidentsByType(final String type) {
		return this.incidentsDAO.findActiveIncidentsByType(type).stream().map(this::populateUpdates).collect(Collectors.toList());
	}

	public Incident create(Incident incident) {
		return dbi.inTransaction((handle, transactionStatus) -> {
			incidentsDAO.create(incident);
			for (IncidentUpdate update : incident.getIncidentUpdates()) {
				updatesDAO.insert(incident.getId(), update);
			}
			return incident;
		});
	}

	public Incident save(Incident incident) {
		return dbi.inTransaction((handle, transactionStatus) -> {
			incidentsDAO.update(incident.getId(), incident.getState(), incident.getUpdatedAt());
			updatesDAO.clear(incident.getId());
			for (IncidentUpdate update : incident.getIncidentUpdates()) {
				updatesDAO.insert(incident.getId(), update);
			}
			return incident;
		});
	}

	public Incident getIncident(String incidentId) {
		Incident incident = incidentsDAO.findById(incidentId);
		List<IncidentUpdate> updates = updatesDAO.findByIncidentId(incidentId);
		return incident.withIncidentUpdatesList(updates);
	}

	private Incident populateUpdates(Incident incident) {
		List<IncidentUpdate> updates = updatesDAO.findByIncidentId(incident.getId());
		return incident.withIncidentUpdatesList(updates);
	}
}
