package ca.uptoeleven.status.db;

import ca.uptoeleven.status.db.models.Incident;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import static ca.uptoeleven.status.db.DBTestHelpers.incidentUpdatesDAO;
import static ca.uptoeleven.status.db.DBTestHelpers.incidentsDAO;
import static ca.uptoeleven.status.db.DBTestHelpers.newIncident;

public class IncidentsDAOTest {

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
        incidentsDAO.insert(newIncident());
    }

    @Test
    public void createdIncidentCanBeSelected() {
        Incident toInsert = newIncident();
        incidentsDAO.insert(toInsert);
        Incident selected = incidentsDAO.findById(toInsert.getId());
        Assert.assertNotNull(selected);
        Assert.assertEquals(toInsert, selected);
    }
}
