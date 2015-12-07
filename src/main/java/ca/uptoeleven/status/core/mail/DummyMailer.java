package ca.uptoeleven.status.core.mail;

import java.io.IOException;

public class DummyMailer implements Mailer {

	private EmailContent lastMail;

	@Override
	public void sendEmail(EmailContent template) throws IOException {
		this.lastMail = template;
	}

	public EmailContent getLastMail() {
		return lastMail;
	}
}
