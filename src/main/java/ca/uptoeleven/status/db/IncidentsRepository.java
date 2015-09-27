package ca.uptoeleven.status.db;

import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.db.models.IncidentUpdate;
import org.skife.jdbi.v2.DBI;

public class IncidentsRepository {

    private final DBI dbi;
    private final IncidentsDAO incidentsDAO;
    private final IncidentUpdatesDAO updatesDAO;

    public IncidentsRepository(DBI dbi, IncidentsDAO incidentsDAO, IncidentUpdatesDAO updatesDAO) {
        this.dbi = dbi;
        this.incidentsDAO = incidentsDAO;
        this.updatesDAO = updatesDAO;
    }

    public void createIncident(final Incident incident, final IncidentUpdate initialUpdate) {
        dbi.inTransaction((handle, transactionStatus) -> {
            incidentsDAO.insert(incident);
            updatesDAO.insert(initialUpdate);
            return null;
        });
    }
}
