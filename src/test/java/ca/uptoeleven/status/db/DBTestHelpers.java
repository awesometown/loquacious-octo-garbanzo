package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.IncidentState;
import ca.uptoeleven.status.core.ServiceStatus;
import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.db.models.IncidentUpdate;
import ca.uptoeleven.status.db.models.Service;
import org.skife.jdbi.v2.DBI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class DBTestHelpers {
    public static IncidentsDAO incidentsDAO(DBI dbi) {
        return dbi.onDemand(IncidentsDAO.class);
    }

    public static IncidentUpdatesDAO incidentUpdatesDAO(DBI dbi) {
        return dbi.onDemand(IncidentUpdatesDAO.class);
    }

    public static ServicesDAO servicesDAO(DBI dbi) {
        return dbi.onDemand(ServicesDAO.class);
    }

    public static Service newService() {
        return new Service(UUID.randomUUID().toString(), "name", "description", ServiceStatus.OK.getId(), LocalDateTime.now(), LocalDateTime.now());
    }

    public static Incident newIncident() {
        return new Incident(UUID.randomUUID().toString(), "title", ServiceStatus.OK.getId(), new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
    }

    public static IncidentUpdate newIncidentUpdate(String incidentId) {
        return new IncidentUpdate(UUID.randomUUID().toString(), incidentId, "foo", IncidentState.INVESTIGATING, null, LocalDateTime.now(), LocalDateTime.now());
    }
}
