package ca.uptoeleven.status.db;

import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.db.models.IncidentUpdate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import static ca.uptoeleven.status.db.DBTestHelpers.*;
import static org.junit.Assert.assertEquals;

public class IncidentsRepositoryTest {
    @Rule
    public H2JDBIRule h2JDBIRule = new H2JDBIRule();

    private DBI dbi;
    private IncidentsDAO incidentsDAO;
    private IncidentUpdatesDAO updatesDAO;
    private IncidentsRepository repository;

    @Before
    public void setup() {
        this.dbi = h2JDBIRule.getDbi();
        this.incidentsDAO = incidentsDAO(h2JDBIRule.getDbi());
        this.updatesDAO = incidentUpdatesDAO(h2JDBIRule.getDbi());
        this.repository = new IncidentsRepository(h2JDBIRule.getDbi(), incidentsDAO, updatesDAO);
    }

    @Test
    public void createIncidentSucceedsWithoutError() {
        Incident incident = newIncident();
        IncidentUpdate update = newIncidentUpdate(incident.getId());
        repository.createIncident(incident, update);
    }

    @Test
    public void createdIncidentCanBeRetrieved() {
        Incident incident = newIncident();
        IncidentUpdate update = newIncidentUpdate(incident.getId());
        repository.createIncident(incident, update);

        Incident found = incidentsDAO.findById(incident.getId());
        assertEquals(incident, found);
    }

    @Test
    public void createdIncidentUpdateCanBeRetrieved() {
        Incident incident = newIncident();
        IncidentUpdate update = newIncidentUpdate(incident.getId());
        repository.createIncident(incident, update);

        IncidentUpdate found = updatesDAO.findById(update.getId());
        assertEquals(update, found);
    }
}
