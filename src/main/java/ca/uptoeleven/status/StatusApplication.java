package ca.uptoeleven.status;

import ca.uptoeleven.status.auth.CustomUnauthorizedHandler;
import ca.uptoeleven.status.auth.DummyAuthenticator;
import ca.uptoeleven.status.auth.DummyAuthorizer;
import ca.uptoeleven.status.core.User;
import ca.uptoeleven.status.db.JDBIModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
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
		bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html", "assets"));

		bootstrap.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Override
	public void run(StatusConfiguration configuration,
					Environment environment) {
		FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORSFilter", CrossOriginFilter.class);

		filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, environment.getApplicationContext().getContextPath() + "*");
		filter.setInitParameter(ALLOWED_METHODS_PARAM, "GET,PUT,POST,OPTIONS");
		filter.setInitParameter(ALLOWED_ORIGINS_PARAM, ALLOWED_ORIGINS);
		filter.setInitParameter(ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept, Authorization");
		filter.setInitParameter(ALLOW_CREDENTIALS_PARAM, "true");
		filter.setInitParameter(EXPOSED_HEADERS_PARAM, "Location");

		environment.jersey().register(new AuthDynamicFeature(
				new BasicCredentialAuthFilter.Builder<User>()
						.setAuthenticator(new DummyAuthenticator())
						.setAuthorizer(new DummyAuthorizer())
						.setUnauthorizedHandler(new CustomUnauthorizedHandler())
						.setRealm("SUPER SECRET STUFF")
						.buildAuthFilter()));
		environment.jersey().register(RolesAllowedDynamicFeature.class);
		//If you want to use @Auth to inject a custom Principal type into your resource
		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
	}
}