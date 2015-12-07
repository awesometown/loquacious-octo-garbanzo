package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import static ca.uptoeleven.status.core.EntityHelpers.newServiceForTest;
import static ca.uptoeleven.status.db.DBTestHelpers.*;
import static ca.uptoeleven.status.db.DBTestHelpers.servicesDAO;

public class ServicesDAOTest {

	@Rule
	public H2JDBIRule h2JDBIRule = new H2JDBIRule();

	private DBI dbi;
	private ServicesDAO servicesDAO;

	@Before
	public void setup() {
		this.dbi = h2JDBIRule.getDbi();
		servicesDAO = servicesDAO(dbi);
	}

	@Test
	public void createServiceSucceedsWithoutError() {
		servicesDAO.insert(newServiceForTest());
	}

	@Test
	public void createdServiceCanBeSelected() {
		Service toInsert = newServiceForTest();
		servicesDAO.insert(toInsert);
		Service selected = servicesDAO.findById(toInsert.getId());
		Assert.assertNotNull(selected);
		Assert.assertEquals(toInsert, selected);
	}
}
