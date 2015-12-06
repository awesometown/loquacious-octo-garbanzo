package ca.uptoeleven.status.core.mail;

import java.io.IOException;

public interface Mailer {
	void sendEmail(EmailTemplate template) throws IOException;
}
