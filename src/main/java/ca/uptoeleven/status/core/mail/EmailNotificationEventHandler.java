package ca.uptoeleven.status.core.mail;

import ca.uptoeleven.status.core.Incident;
import com.google.inject.Inject;

public class EmailNotificationEventHandler {

	private final Mailer mailer;

	@Inject
	public EmailNotificationEventHandler(Mailer mailer) {
		this.mailer = mailer;
	}


	public void onIncidentCreated(Incident newIncident) {
		try {
			NewIncidentSubstitutions subs = new NewIncidentSubstitutions(newIncident);
			FreemarkerEmailTemplate templ = new FreemarkerEmailTemplate("Hi ${incident.id}");
			templ.setSubstitutions(subs);
			
			String output = templ.getHtmlContent();
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onIncidentUpdated(Incident updatedIncident) {
	}
}
