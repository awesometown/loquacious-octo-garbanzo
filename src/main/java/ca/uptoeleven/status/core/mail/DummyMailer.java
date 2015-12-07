package ca.uptoeleven.status.core.mail;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class DummyMailer implements Mailer {

	private EmailContent lastMail;

	@Override
	public void sendEmail(EmailContent template) throws IOException {
		log.info("Pretending to send email: " + template.getHtmlContent());
		this.lastMail = template;
	}

	public EmailContent getLastMail() {
		return lastMail;
	}
}
