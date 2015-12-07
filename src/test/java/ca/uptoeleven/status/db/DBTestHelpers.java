package ca.uptoeleven.status.db;

import org.skife.jdbi.v2.DBI;

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

}
