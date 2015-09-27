package ca.uptoeleven.status.db;

import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.db.models.IncidentUpdate;
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

    public static Incident newIncident() {
        return new Incident(UUID.randomUUID().toString(), "title", "ok", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
    }

    public static IncidentUpdate newIncidentUpdate(String incidentId) {
        return new IncidentUpdate(UUID.randomUUID().toString(), incidentId, "foo", "bar", null, LocalDateTime.now(), LocalDateTime.now());
    }
}
