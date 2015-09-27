package ca.uptoeleven.status.db;

import ca.uptoeleven.status.StatusConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class JDBIModule extends AbstractModule {

    public JDBIModule() {
    }

    @Provides
    public DBI jdbi(Environment environment, StatusConfiguration configuration) {
        return new DBIFactory().build(environment, configuration.getDataSourceFactory(), "h2");
    }

    @Provides
    public IncidentsDAO getIncidentsDAO(DBI jdbi) {
        return jdbi.onDemand(IncidentsDAO.class);
    }

    @Provides
    public ServiceStatusesDAO getServiceStatusesDAO(DBI jdbi) { return jdbi.onDemand(ServiceStatusesDAO.class); }

    @Override
    protected void configure() {

    }
}
