package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.*;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import java.util.List;

import static ca.uptoeleven.status.db.DBTestHelpers.*;
import static org.junit.Assert.*;

public class IncidentsDAOTest {

	@Rule
	public H2JDBIRule h2JDBIRule = new H2JDBIRule();

	private DBI dbi;
	private IncidentsDAO incidentsDAO;
	private ServicesDAO servicesDAO;

	@Before
	public void setup() {
		this.dbi = h2JDBIRule.getDbi();
		this.incidentsDAO = incidentsDAO(h2JDBIRule.getDbi());
		this.servicesDAO = servicesDAO(h2JDBIRule.getDbi());
	}

	@Test
	public void findActiveIncidentsOmitsResolvedIncidents() {
		Incident open = newIncidentForTest().withState(IncidentState.Unplanned.INVESTIGATING);
		Incident resolved = newIncidentForTest().withState(IncidentState.Unplanned.RESOLVED);
		incidentsDAO.create(open);
		incidentsDAO.create(resolved);

		List<Incident> incidents = incidentsDAO.findActiveIncidents();
		assertEquals(1, incidents.size());
		assertEquals(open.getId(), incidents.get(0).getId());
	}

	@Test
	public void insertIncidentSucceedsWithoutError() {
		incidentsDAO.insert(newIncidentWithUpdateForTest().withId(newId()));
	}

	@Test
	public void createIncidentSetsId() {
		Incident incident = newIncidentWithUpdateForTest();
		incident = incidentsDAO.create(incident);
		assertNotNull(incident.getId());
	}

	@Test
	public void createIncidentSavesAffectedServiceId() {
		Service service = newServiceForTest().withId(newId());
		servicesDAO.insert(service);

		Incident incident = newIncidentWithUpdateForTest().withAffectedServicesIdsList(Lists.newArrayList(service.getId()));
		incident = incidentsDAO.create(incident);
		List<String> affectedIds = incidentsDAO.findAffectedServiceIds(incident.getId());
		assertEquals(1, affectedIds.size());
		assertEquals(service.getId(), affectedIds.get(0));
	}

	@Test
	public void createdIncidentCanBeSelected() {
		Incident toInsert = newIncidentForTest();
		toInsert = incidentsDAO.create(toInsert);
		Incident selected = incidentsDAO.findById(toInsert.getId());
		assertNotNull(selected);
		assertEquals(IncidentType.UNPLANNED, selected.getType());
		assertEquals(toInsert, selected);
	}

	@Test
	public void createPlannedIncident() {

		final Incident expected = this.incidentsDAO.create(newPlannedIncidentForTest());
		final Incident actual = this.incidentsDAO.findById(expected.getId());

		assertEquals(expected, actual);
		assertEquals(IncidentType.PLANNED, expected.getType());
	}

	@Test
	public void testGetAllIncidents() {

		final Incident planned = this.incidentsDAO.create(newPlannedIncidentForTest());
		final Incident unplanned = this.incidentsDAO.create(newIncidentForTest());

		final List<Incident> allIncidents = this.incidentsDAO.findAllIncidents();

		assertTrue(allIncidents.contains(planned));
		assertTrue(allIncidents.contains(unplanned));
	}

	@Test
	public void testGetPlannedIncidents() {

		final Incident planned = this.incidentsDAO.create(newPlannedIncidentForTest());
		final Incident unplanned = this.incidentsDAO.create(newIncidentForTest());

		final List<Incident> allIncidents = this.incidentsDAO.findAllIncidentsByType(IncidentType.PLANNED);

		assertTrue(allIncidents.contains(planned));
		assertFalse(allIncidents.contains(unplanned));
	}

	@Test
	public void testGetUnplannedIncidents() {

		final Incident planned = this.incidentsDAO.create(newPlannedIncidentForTest());
		final Incident unplanned = this.incidentsDAO.create(newIncidentForTest());

		final List<Incident> allIncidents = this.incidentsDAO.findAllIncidentsByType(IncidentType.UNPLANNED);

		assertFalse(allIncidents.contains(planned));
		assertTrue(allIncidents.contains(unplanned));
	}

	@Test
	public void testGetActivePlannedIncidents() {

		final Incident planned = this.incidentsDAO.create(newPlannedIncidentForTest());
		final Incident unplanned = this.incidentsDAO.create(newIncidentForTest());
		final Incident plannedNotActive = this.incidentsDAO.create(newPlannedIncidentForTest().withState(IncidentState.Planned.COMPLETED));
		final Incident unplannedNotActive = this.incidentsDAO.create(newIncidentForTest().withState(IncidentState.Unplanned.RESOLVED));

		final List<Incident> allIncidents = this.incidentsDAO.findActiveIncidentsByType(IncidentType.PLANNED);

		assertTrue(allIncidents.contains(planned));
		assertFalse(allIncidents.contains(plannedNotActive));
		assertFalse(allIncidents.contains(unplanned));
		assertFalse(allIncidents.contains(unplannedNotActive));

	}

	@Test
	public void testGetActiveUnplannedIncidents() {

		final Incident planned = this.incidentsDAO.create(newPlannedIncidentForTest());
		final Incident unplanned = this.incidentsDAO.create(newIncidentForTest());
		final Incident plannedNotActive = this.incidentsDAO.create(newPlannedIncidentForTest().withState(IncidentState.Planned.COMPLETED));
		final Incident unplannedNotActive = this.incidentsDAO.create(newIncidentForTest().withState(IncidentState.Unplanned.RESOLVED));

		final List<Incident> allIncidents = this.incidentsDAO.findActiveIncidentsByType(IncidentType.UNPLANNED);

		assertFalse(allIncidents.contains(planned));
		assertFalse(allIncidents.contains(plannedNotActive));
		assertTrue(allIncidents.contains(unplanned));
		assertFalse(allIncidents.contains(unplannedNotActive));
	}

}
