package ca.uptoeleven.status.core.mail;

import java.io.IOException;

public class DummyMailer implements Mailer {

	private EmailTemplate lastMail;

	@Override
	public void sendEmail(EmailTemplate template) throws IOException {
		this.lastMail = template;
	}

	public EmailTemplate getLastMail() {
		return lastMail;
	}
}
