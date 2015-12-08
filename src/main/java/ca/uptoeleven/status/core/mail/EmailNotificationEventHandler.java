package ca.uptoeleven.status.core.mail;

import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.core.events.IncidentCreatedEvent;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Provider;
import freemarker.template.Configuration;

public class EmailNotificationEventHandler {

	private final TemplateFactory templateFactory;
	private final Mailer mailer;

	@Inject
	public EmailNotificationEventHandler(TemplateFactory templateFactory, Mailer mailer) {
		this.templateFactory = templateFactory;
		this.mailer = mailer;
	}


	@Subscribe
	public void onIncidentCreated(IncidentCreatedEvent event) {
		try {
			NewIncidentSubstitutions subs = new NewIncidentSubstitutions(event.getIncident());
			EmailTemplate template = templateFactory.getTemplate("new_incident");
			EmailContent content = template.resolve(subs);

			mailer.sendEmail(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onIncidentUpdated(Incident updatedIncident) {
	}
}
