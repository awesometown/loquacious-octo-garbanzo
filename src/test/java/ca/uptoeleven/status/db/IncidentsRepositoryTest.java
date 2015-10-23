package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.Incident;
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
    private ServicesDAO servicesDAO;
    private IncidentsRepository repository;

    @Before
    public void setup() {
        this.dbi = h2JDBIRule.getDbi();
        this.incidentsDAO = incidentsDAO(h2JDBIRule.getDbi());
        this.updatesDAO = incidentUpdatesDAO(h2JDBIRule.getDbi());
        this.servicesDAO = servicesDAO(h2JDBIRule.getDbi());
        this.repository = new IncidentsRepository(h2JDBIRule.getDbi(), incidentsDAO, updatesDAO, servicesDAO);
    }

    @Test
    public void createIncidentSucceedsWithoutError() {
        Incident incident = newIncidentWithUpdateForTest();
        repository.create(incident);
    }

    @Test
    public void createdIncidentCanBeRetrieved() {
        Incident incident = newIncidentForTest();
        incident = repository.create(incident);

        Incident found = incidentsDAO.findById(incident.getId());
        assertEquals(incident, found);
    }

    @Test
    public void createdIncidentUpdateIsRetrievedWithIncident() {
        Incident incident = newIncidentWithUpdateForTest();
        Incident created = repository.create(incident);

        Incident retrieved = repository.getIncident(created.getId());
        assertEquals(1, retrieved.getIncidentUpdates().size());
    }
}
