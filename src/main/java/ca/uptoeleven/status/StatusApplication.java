package ca.uptoeleven.status;

import ca.uptoeleven.status.db.IncidentsDAO;
import ca.uptoeleven.status.db.JDBIModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.nio.charset.Charset;
import java.util.EnumSet;

import static org.eclipse.jetty.servlets.CrossOriginFilter.*;

public class StatusApplication extends Application<StatusConfiguration> {

	private static final String ALLOWED_ORIGINS = "*";

	public static void main(String[] args) throws Exception {
		new StatusApplication().run(args);
	}

	private GuiceBundle<StatusConfiguration> guiceBundle;

	@Override
	public String getName() {
		return "status";
	}

	@Override
	public void initialize(Bootstrap<StatusConfiguration> bootstrap) {
		guiceBundle = GuiceBundle.<StatusConfiguration>newBuilder()
				.addModule(new StatusModule())
				.addModule(new JDBIModule())
				.setConfigClass(StatusConfiguration.class)
				.enableAutoConfig(getClass().getPackage().getName())
				.build();

		bootstrap.addBundle(guiceBundle);
		bootstrap.addBundle(new DBIExceptionsBundle());
		bootstrap.addBundle(new Java8Bundle());
		bootstrap.addBundle(new MigrationsBundle<StatusConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(StatusConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});
		bootstrap.addBundle(new ViewBundle<>());
		bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html", "assets"));
		bootstrap.addBundle(new AssetsBundle("/META-INF/resources/webjars", "/webjars", null, "webjars"));

		bootstrap.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Override
	public void run(StatusConfiguration configuration,
	                Environment environment) {
		FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORSFilter", CrossOriginFilter.class);

		filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, environment.getApplicationContext().getContextPath() + "*");
		filter.setInitParameter(ALLOWED_METHODS_PARAM, "GET,PUT,POST,OPTIONS");
		filter.setInitParameter(ALLOWED_ORIGINS_PARAM, ALLOWED_ORIGINS);
		filter.setInitParameter(ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept");
		filter.setInitParameter(ALLOW_CREDENTIALS_PARAM, "true");
		filter.setInitParameter(EXPOSED_HEADERS_PARAM, "Location");
	}
}