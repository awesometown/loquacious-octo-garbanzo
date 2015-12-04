package ca.uptoeleven.status.auth;

import ca.uptoeleven.status.core.User;
import io.dropwizard.auth.Authorizer;

public class DummyAuthorizer implements Authorizer<User> {

	@Override
	public boolean authorize(User user, String s) {
		return true;
	}

}
