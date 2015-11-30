package ca.uptoeleven.status.auth;

import ca.uptoeleven.status.core.User;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.auth.Authenticator;

public class DummyAuthenticator implements Authenticator<BasicCredentials, User> {

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		if ("secret".equals(credentials.getPassword())) {
			return Optional.of(new User(credentials.getUsername()));
		}
		return Optional.absent();
	}

}