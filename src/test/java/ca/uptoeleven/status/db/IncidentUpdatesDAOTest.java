package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.core.IncidentUpdate;
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

	@Before
	public void setup() {
		this.dbi = h2JDBIRule.getDbi();
		this.incidentsDAO = incidentsDAO(h2JDBIRule.getDbi());
		this.updatesDAO = incidentUpdatesDAO(h2JDBIRule.getDbi());
	}

	@Test
	public void insertSucceedsWithoutError() {
		Incident incident = newIncidentWithUpdateForTest();
		incident = incidentsDAO.create(incident);

		IncidentUpdate update = newIncidentUpdateForTest();
		updatesDAO.insert(incident.getId(), update);
	}

	@Test
	public void findByIdReturnsUpdate() {
		Incident incident = newIncidentWithUpdateForTest();
		incident = incidentsDAO.create(incident);

		IncidentUpdate update = newIncidentUpdateForTest();
		updatesDAO.insert(incident.getId(), update);

		IncidentUpdate found = updatesDAO.findById(update.getId());
		assertEquals(update, found);
	}

	@Test
	public void findByIncidentIdReturnsAllUpdates() {
		Incident incident = newIncidentWithUpdateForTest();
		incident = incidentsDAO.create(incident);

		IncidentUpdate update1 = newIncidentUpdateForTest();
		updatesDAO.insert(incident.getId(), update1);
		IncidentUpdate update2 = newIncidentUpdateForTest();
		updatesDAO.insert(incident.getId(), update2);

		List<IncidentUpdate> found = updatesDAO.findByIncidentId(incident.getId());
		assertEquals(2, found.size());
		assertTrue(found.contains(update1));
		assertTrue(found.contains(update2));
	}

}
