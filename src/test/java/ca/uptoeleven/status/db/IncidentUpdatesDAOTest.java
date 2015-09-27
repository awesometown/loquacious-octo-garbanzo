package ca.uptoeleven.status.db;

import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.db.models.IncidentUpdate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import java.util.List;

import static ca.uptoeleven.status.db.DBTestHelpers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IncidentUpdatesDAOTest {

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
    public void insertSucceedsWithoutError() {
        Incident incident = newIncident();
        incidentsDAO.insert(incident);

        IncidentUpdate update = newIncidentUpdate(incident.getId());
        updatesDAO.insert(update);
    }

    @Test
    public void findByIdReturnsUpdate() {
        Incident incident = newIncident();
        incidentsDAO.insert(incident);

        IncidentUpdate update = newIncidentUpdate(incident.getId());
        updatesDAO.insert(update);

        IncidentUpdate found = updatesDAO.findById(update.getId());
        assertEquals(update, found);
    }

    @Test
    public void findByIncidentIdReturnsAllUpdates() {
        Incident incident = newIncident();
        incidentsDAO.insert(incident);

        IncidentUpdate update1 = newIncidentUpdate(incident.getId());
        updatesDAO.insert(update1);
        IncidentUpdate update2 = newIncidentUpdate(incident.getId());
        updatesDAO.insert(update2);

        List<IncidentUpdate> found = updatesDAO.findByIncidentId(incident.getId());
        assertEquals(2, found.size());
        assertTrue(found.contains(update1));
        assertTrue(found.contains(update2));
    }
}
