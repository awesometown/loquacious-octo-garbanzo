package ca.uptoeleven.status.mail;

import org.junit.Ignore;
import org.junit.Test;

public class MailerTest {

    @Test
    @Ignore
    public void sendMail() throws Exception {
        EmailTemplate template = new EmailTemplate("# Hello world!\n\nThis is an email!");
        Mailer.sendEmail(template);
    }
}
