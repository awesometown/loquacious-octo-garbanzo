package ca.uptoeleven.status.db;

import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.db.models.IncidentUpdate;
import org.skife.jdbi.v2.DBI;

import java.util.Optional;

public class IncidentsRepository {

    private final DBI dbi;
    private final IncidentsDAO incidentsDAO;
    private final IncidentUpdatesDAO updatesDAO;
    private final ServicesDAO servicesDAO;

    public IncidentsRepository(DBI dbi, IncidentsDAO incidentsDAO, IncidentUpdatesDAO updatesDAO, ServicesDAO servicesDAO) {
        this.dbi = dbi;
        this.incidentsDAO = incidentsDAO;
        this.updatesDAO = updatesDAO;
        this.servicesDAO = servicesDAO;
    }

    public void createIncident(final Incident incident, final IncidentUpdate initialUpdate) {
        dbi.inTransaction((handle, transactionStatus) -> {
            incidentsDAO.insert(incident);
            updatesDAO.insert(initialUpdate);
            for(String serviceId : incident.getAffectedServicesIds()) {
                servicesDAO.updateServiceStatusId(serviceId, initialUpdate.getNewServiceStatusId());
            }
            return null;
        });
    }
}
