package ca.uptoeleven.status.core.mail;

import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.core.events.IncidentCreatedEvent;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public class EmailNotificationEventHandler {

	private final Mailer mailer;

	@Inject
	public EmailNotificationEventHandler(Mailer mailer) {
		this.mailer = mailer;
	}


	@Subscribe
	public void onIncidentCreated(IncidentCreatedEvent event) {
		try {
			NewIncidentSubstitutions subs = new NewIncidentSubstitutions(event.getIncident());
			FreemarkerEmailTemplate templ = new FreemarkerEmailTemplate("Hi ${incident.id}");
			EmailContent content = templ.resolve(subs);

			mailer.sendEmail(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onIncidentUpdated(Incident updatedIncident) {
	}
}
