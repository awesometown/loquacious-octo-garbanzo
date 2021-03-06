package ca.uptoeleven.status.core;

import java.util.ArrayList;

import static ca.uptoeleven.status.core.UtcDateTime.nowUtcZoned;

public class EntityHelpers {
	public static Service newServiceForTest() {
		return Service.createNew("name", "description");
	}

	public static Incident newIncidentWithUpdateForTest() {
		return Incident.newIncident("title", "updateDescription", IncidentState.Unplanned.INVESTIGATING, ServiceStatus.OK, new ArrayList<>());
	}

	public static Incident newIncidentForTest() {
		return Incident.newIncident("title", "notUsed", IncidentState.Unplanned.INVESTIGATING, ServiceStatus.OK, new ArrayList<>()).withIncidentUpdatesList(new ArrayList<>());
	}

	public static Incident newPlannedIncidentForTest() {
		return Incident.newPlannedIncident("title", "notUsed", IncidentState.Planned.STARTED, ServiceStatus.OK, new ArrayList<>(), nowUtcZoned().plusHours(1)).withIncidentUpdatesList(new ArrayList<>());
	}

	public static IncidentUpdate newIncidentUpdateForTest() {
		return IncidentUpdate.createNew("foo", IncidentState.Unplanned.INVESTIGATING, ServiceStatus.OK);
	}

}
