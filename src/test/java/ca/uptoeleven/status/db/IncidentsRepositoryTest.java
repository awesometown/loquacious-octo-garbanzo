package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.IncidentState;
import ca.uptoeleven.status.core.ServiceStatus;
import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.core.IncidentUpdate;
import ca.uptoeleven.status.core.Service;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import java.util.List;

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
        IncidentUpdate update = newIncidentUpdate();
        repository.createIncident(incident, update);
    }

    @Test
    public void createdIncidentCanBeRetrieved() {
        Incident incident = newIncident();
        IncidentUpdate update = newIncidentUpdate();
        incident = repository.createIncident(incident, update);

        Incident found = incidentsDAO.findById(incident.getId());
        assertEquals(incident, found);
    }

    @Test
    public void createdIncidentUpdateCanBeRetrieved() {
        Incident incident = newIncident();
        IncidentUpdate update = newIncidentUpdate();
        Incident created = repository.createIncident(incident, update);

        List<IncidentUpdate> found = updatesDAO.findByIncidentId(created.getId());
        assertEquals(1, found.size());
        assertEquals(created.getId(), found.get(0).getIncidentId());
    }

    @Test
    public void createdIncidentResultsInUpdatedServiceStatus() {
        Service service = DBTestHelpers.newService().withServiceStatusId(ServiceStatus.OK.getId());
        servicesDAO.insert(service);
        Incident incident = newIncident().withAffectedServicesIds(Lists.newArrayList(service.getId()));
        IncidentUpdate update = newIncidentUpdate().withNewServiceStatusId(ServiceStatus.DEGRADED.getId());
        repository.createIncident(incident, update);

        service = servicesDAO.findById(service.getId());
        assertEquals(ServiceStatus.DEGRADED.getId(), service.getServiceStatusId());
    }

    @Test
    //test fails until affected serviceids actually get stored with the incident
    public void incidentUpdateResultsInUpdatedServiceStatus() {
        Service service = DBTestHelpers.newService().withServiceStatusId(ServiceStatus.OK.getId());
        servicesDAO.insert(service);
        Incident incident = newIncident().withAffectedServicesIds(Lists.newArrayList(service.getId()));
        IncidentUpdate initialUpdate = newIncidentUpdate().withNewServiceStatusId(ServiceStatus.DEGRADED.getId());
        Incident created = repository.createIncident(incident, initialUpdate);

        IncidentUpdate secondUpdate = newIncidentUpdate()
                .withIncidentId(created.getId())
                .withNewServiceStatusId(ServiceStatus.MAJOR.getId());
        repository.updateIncident(secondUpdate);

        service = servicesDAO.findById(service.getId());
        assertEquals(ServiceStatus.MAJOR.getId(), service.getServiceStatusId());
    }

    @Test
    public void incidentUpdateResultsInUpdatedIncidentState() {
        Service service = DBTestHelpers.newService().withServiceStatusId(ServiceStatus.OK.getId());
        servicesDAO.insert(service);
        Incident incident = newIncident().withAffectedServicesIds(Lists.newArrayList(service.getId()));
        IncidentUpdate initialUpdate = newIncidentUpdate().withNewServiceStatusId(ServiceStatus.DEGRADED.getId());
        Incident created = repository.createIncident(incident, initialUpdate);

        IncidentUpdate secondUpdate = initialUpdate
                .withIncidentId(created.getId())
                .withNewState(IncidentState.MONITORING);
        repository.updateIncident(secondUpdate);

        incident = incidentsDAO.findById(created.getId());
        assertEquals(IncidentState.MONITORING, incident.getState());
    }
}
