package ca.uptoeleven.status.api;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class IncidentViewModel {

	private final String id;

	private final String title;

	private final String state;

	private final String type;

	private final String serviceStatusId;

	private final List<String> affectedServiceIds;

	private final ZonedDateTime createdAt;

	private final ZonedDateTime updatedAt;

	private final ZonedDateTime startTime;

	private final List<IncidentUpdateViewModel> incidentUpdates;

}
