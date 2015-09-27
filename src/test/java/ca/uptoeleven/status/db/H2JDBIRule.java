package ca.uptoeleven.status.db;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.java8.jdbi.args.LocalDateTimeArgumentFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.rules.ExternalResource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

public class H2JDBIRule extends ExternalResource {

    private DBI dbi;

    private Handle handle;

    private Liquibase liquibase;

    public DBI getDbi() {
        return dbi;
    }

    @Override
    protected void before() throws Throwable {
        Environment environment = new Environment("test-env", Jackson.newObjectMapper(), null, new MetricRegistry(), null);
        dbi = new DBIFactory().build(environment, getDataSourceFactory(), "test");
        dbi.registerArgumentFactory(new LocalDateTimeArgumentFactory());
        handle = dbi.open();
        migrateDatabase();
    }

    @Override
    protected void after() {
        try {
            liquibase.dropAll();
        } catch (Exception e) {
            throw new RuntimeException("failed clearing up Liquibase object", e);
        }
        handle.close();
    }

    private void migrateDatabase() throws LiquibaseException {
        liquibase = new Liquibase("migrations.xml", new ClassLoaderResourceAccessor(), new JdbcConnection(handle.getConnection()));
        liquibase.update("");
    }

    private DataSourceFactory getDataSourceFactory() {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass("org.h2.Driver" );
        dataSourceFactory.setUrl("jdbc:h2:./build/h2db" );
        dataSourceFactory.setUser("sa" );
        dataSourceFactory.setPassword("sa");
        return dataSourceFactory;
    }
}