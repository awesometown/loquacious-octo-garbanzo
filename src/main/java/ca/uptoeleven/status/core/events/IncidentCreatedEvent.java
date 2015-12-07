package ca.uptoeleven.status.core.events;

import ca.uptoeleven.status.core.Incident;
import lombok.Data;

@Data
public class IncidentCreatedEvent {
	private final Incident incident;
}
