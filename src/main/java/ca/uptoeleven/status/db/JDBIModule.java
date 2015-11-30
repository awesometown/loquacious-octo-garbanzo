package ca.uptoeleven.status.db;

import ca.uptoeleven.status.StatusConfiguration;
import ca.uptoeleven.status.core.DefaultIncidentService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.java8.jdbi.args.LocalDateTimeArgumentFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class JDBIModule extends AbstractModule {

	public JDBIModule() {
	}

	@Provides
	public DBI jdbi(Environment environment, StatusConfiguration configuration) {
		DBI dbi = new DBIFactory().build(environment, configuration.getDataSourceFactory(), "h2");
		dbi.registerArgumentFactory(new LocalDateTimeArgumentFactory());
		return dbi;
	}

	@Provides
	public IncidentsDAO getIncidentsDAO(DBI jdbi) {
		return jdbi.onDemand(IncidentsDAO.class);
	}

	@Provides
	public IncidentUpdatesDAO getIncidentUpdatesDAO(DBI jdbi) {
		return jdbi.onDemand(IncidentUpdatesDAO.class);
	}

	@Provides
	public ServiceStatusesDAO getServiceStatusesDAO(DBI jdbi) {
		return jdbi.onDemand(ServiceStatusesDAO.class);
	}

	@Provides
	public ServicesDAO getServicesDAO(DBI jdbi) {
		return jdbi.onDemand(ServicesDAO.class);
	}

	@Override
	protected void configure() {
		bind(IncidentsRepository.class);
		bind(DefaultIncidentService.class);
	}

}
