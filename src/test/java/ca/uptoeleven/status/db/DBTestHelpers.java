package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.*;
import org.skife.jdbi.v2.DBI;

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

    public static String newId() {
        return UUID.randomUUID().toString();
    }

    public static Service newServiceForTest() {
        return Service.createNew("name", "description");
    }

    public static Incident newIncidentWithUpdateForTest() {
        return Incident.newIncident("title", "updateDescription", IncidentState.INVESTIGATING, ServiceStatus.OK.getId(), new ArrayList<>());
    }

	public static Incident newIncidentForTest() {
		return Incident.newIncident("title", "notUsed", IncidentState.INVESTIGATING, ServiceStatus.OK.getId(), new ArrayList<>()).withIncidentUpdatesList(new ArrayList<>());
	}

    public static IncidentUpdate newIncidentUpdateForTest() {
        return IncidentUpdate.createNew("foo", IncidentState.INVESTIGATING, ServiceStatus.OK.getId());
    }
}
