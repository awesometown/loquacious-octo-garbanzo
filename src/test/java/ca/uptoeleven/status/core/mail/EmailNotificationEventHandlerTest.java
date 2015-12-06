package ca.uptoeleven.status.core.mail;

import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.core.IncidentState;
import ca.uptoeleven.status.core.ServiceStatus;
import org.junit.Test;

import java.util.ArrayList;

public class EmailNotificationEventHandlerTest {

	@Test
	public void testOnIncidentCreatedCreatesExpectedEmail() {
		DummyMailer mailer = new DummyMailer();
		EmailNotificationEventHandler handler = new EmailNotificationEventHandler(mailer);

		Incident newIncident = Incident.newIncident("title", "update", IncidentState.Unplanned.IDENTIFIED, ServiceStatus.DEGRADED.getId(), new ArrayList<>());
		handler.onIncidentCreated(newIncident);
		System.out.println(mailer.getLastMail().getHtmlContent());
	}
}
