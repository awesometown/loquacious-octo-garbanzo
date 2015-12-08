package ca.uptoeleven.status.core.mail;

import org.junit.Ignore;
import org.junit.Test;

public class SESMailerTest {

	@Test
	@Ignore
	public void sendMail() throws Exception {
		EmailContent content = new EmailContent("Hello", "Hi");
		new SESMailer().sendEmail(content);
	}

}
