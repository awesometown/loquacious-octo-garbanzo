package ca.uptoeleven.status.core.mail;

import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.core.IncidentState;
import ca.uptoeleven.status.core.ServiceStatus;
import ca.uptoeleven.status.core.events.IncidentCreatedEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class EmailNotificationEventHandlerTest {

	@Mock
	private TemplateFactory templateFactory;

	@Before
	public void init() {
		initMocks(this);
	}

	@Test
	public void testOnIncidentCreatedCreatesExpectedEmail() {
		DummyMailer mailer = new DummyMailer();
		EmailNotificationEventHandler handler = new EmailNotificationEventHandler(templateFactory, mailer);

		EmailTemplate template = new EmailTemplate() {
			@Override
			public EmailContent resolve(TemplateSubstitutions substitutions) throws TemplateException {
				return new EmailContent("hi there", "hi there");
			}
		};
		when(templateFactory.getTemplate(any())).thenReturn(template);

		Incident newIncident = Incident.newIncident("title", "update", IncidentState.Unplanned.IDENTIFIED, ServiceStatus.DEGRADED, new ArrayList<>());
		handler.onIncidentCreated(new IncidentCreatedEvent(newIncident));

		assertEquals("hi there", mailer.getLastMail().getHtmlContent());
	}
}
