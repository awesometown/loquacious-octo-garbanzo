package ca.uptoeleven.status.core.mail;

import com.google.inject.AbstractModule;

public class EmailModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Mailer.class).to(DummyMailer.class);
		bind(EmailNotificationEventHandler.class).asEagerSingleton();
	}
}
