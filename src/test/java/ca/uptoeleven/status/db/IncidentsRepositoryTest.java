package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.ServiceStatus;
import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.db.models.IncidentUpdate;
import ca.uptoeleven.status.db.models.Service;
import com.google.common.collect.Lists;
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

    @Test
    public void createdIncidentResultsInUpdatedServiceStatus() {
        Service service = DBTestHelpers.newService().withServiceStatusId("ok");
        servicesDAO.insert(service);
        Incident incident = newIncident().withAffectedServicesIds(Lists.newArrayList(service.getId()));
        IncidentUpdate update = newIncidentUpdate(incident.getId()).withNewServiceStatusId(ServiceStatus.DEGRADED.getId());
        repository.createIncident(incident, update);

        service = servicesDAO.findById(service.getId());
        assertEquals(ServiceStatus.DEGRADED.getId(), service.getServiceStatusId());
    }
}
