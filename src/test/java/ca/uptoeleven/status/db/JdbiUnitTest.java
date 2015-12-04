package ca.uptoeleven.status.db;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.After;
import org.junit.Before;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

public abstract class JdbiUnitTest {

	protected DBI dbi;

	private Handle handle;

	private Liquibase liquibase;

	protected abstract void setUpDataAccessObjects();

	@Before
	public void setUpDatabase() throws Exception {

		Environment environment = new Environment("test-env", Jackson.newObjectMapper(), null, new MetricRegistry(), null);
		dbi = new DBIFactory().build(environment, getDataSourceFactory(), "test");
		handle = dbi.open();
		migrateDatabase();
		setUpDataAccessObjects();
	}

	@After
	public void tearDown() throws Exception {
		liquibase.dropAll();
		handle.close();
	}

	private void migrateDatabase() throws LiquibaseException {
		liquibase = new Liquibase("migrations.xml", new ClassLoaderResourceAccessor(), new JdbcConnection(handle.getConnection()));
		liquibase.update("");
	}

	protected DataSourceFactory getDataSourceFactory() {
		DataSourceFactory dataSourceFactory = new DataSourceFactory();
		dataSourceFactory.setDriverClass("org.h2.Driver");
		dataSourceFactory.setUrl("jdbc:h2:./build/h2db");
		dataSourceFactory.setUser("sa");
		dataSourceFactory.setPassword("sa");

		return dataSourceFactory;
	}
}