package ca.uptoeleven.status;

import ca.uptoeleven.status.db.IncidentsDAO;
import ca.uptoeleven.status.db.JDBIModule;
import ca.uptoeleven.status.resources.api.ServicesResource;
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
import org.skife.jdbi.v2.DBI;

public class StatusApplication extends Application<StatusConfiguration> {
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
		bootstrap.addBundle(new AssetsBundle("/assets/", "/assets/", null, "assets"));
		bootstrap.addBundle(new AssetsBundle("/META-INF/resources/webjars", "/webjars", null, "webjars" ));
	}

	@Override
	public void run(StatusConfiguration configuration,
	                Environment environment) {
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");
		final IncidentsDAO dao = jdbi.onDemand(IncidentsDAO.class);
		environment.jersey().register(new ServicesResource());
		//environment.jersey().register(remoteResource);
		//environment.jersey().register(serialResource);
		//environment.jersey().register(asyncResource);
		//environment.jersey().register(reactiveResource);
	}

}