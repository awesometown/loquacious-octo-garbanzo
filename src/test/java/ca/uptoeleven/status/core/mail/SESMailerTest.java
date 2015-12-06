package ca.uptoeleven.status.core.mail;

import org.junit.Ignore;
import org.junit.Test;

public class SESMailerTest {

	@Test
	@Ignore
	public void sendMail() throws Exception {
		EmailTemplate template = new EmailTemplate("# Hello world!\n\nThis is an email!");
		SESMailer.sendEmail(template);
	}

}
