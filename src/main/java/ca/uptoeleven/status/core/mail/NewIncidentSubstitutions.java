package ca.uptoeleven.status.core.mail;

import ca.uptoeleven.status.core.Incident;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewIncidentSubstitutions extends TemplateSubstitutions {
	private final Incident incident;
}
