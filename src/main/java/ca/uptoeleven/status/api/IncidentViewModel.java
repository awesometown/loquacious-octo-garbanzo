package ca.uptoeleven.status.api;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class IncidentViewModel {
	private final String id;
	private final String title;
	private final String state;
	private final String serviceStatusId;
	private final List<String> affectedServiceIds;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;
	private final List<IncidentUpdateViewModel> incidentUpdates;
}
