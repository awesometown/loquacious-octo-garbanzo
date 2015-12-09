package ca.uptoeleven.status.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static ca.uptoeleven.status.core.IdGenerator.newId;
import static ca.uptoeleven.status.core.UtcDateTime.nowUtc;

@Data
@Wither
@AllArgsConstructor
public class Incident {

	public static Incident newIncident(
			final String title,
			final String firstUpdateDescription,
			final String state,
			final String serviceStatusId,
			final List<String> affectedServicesIds) {
		return newIncident(title, firstUpdateDescription, state, IncidentType.UNPLANNED, serviceStatusId, affectedServicesIds, null);
	}

	public static Incident newPlannedIncident(final String title, final String firstUpdateDescription, final String state,
											  final String serviceStatusId, final List<String> affectedServicesIds, ZonedDateTime startTime) {
		return newIncident(title, firstUpdateDescription, state, IncidentType.PLANNED, serviceStatusId, affectedServicesIds, startTime);
	}

	public static Incident newIncident(final String title, final String firstUpdateDescription, final String state,
			final String type, final String serviceStatusId, final List<String> affectedServicesIds, ZonedDateTime startTime) {
		final LocalDateTime now = nowUtc();
		final LocalDateTime localStartTime = startTime == null ? now : startTime.toLocalDateTime();
		final IncidentUpdate update = new IncidentUpdate(newId(), firstUpdateDescription, state, serviceStatusId, now, now);
		return new Incident(
				newId(),
				title,
				state,
				type,
				ImmutableList.copyOf(affectedServicesIds),
				localStartTime,
				now,
				now,
				ImmutableList.of(update));
	}

	public Incident(
			final String id,
			final String title,
			final String state,
			final String type,
			final List<String> affectedServicesIds,
			final LocalDateTime startTime,
			final LocalDateTime createdAt,
			final LocalDateTime updatedAt,
			final List<IncidentUpdate> incidentUpdates) {
		this(id, title, state, type, ImmutableList.copyOf(affectedServicesIds), startTime, createdAt, updatedAt,
				incidentUpdates == null ? ImmutableList.of() : ImmutableList.copyOf(incidentUpdates));
	}

	@NotNull
	@JsonProperty
	private final String id;

	@NotNull
	@JsonProperty
	private final String title;

	@NotNull
	@JsonProperty
	private final String state;

	@NotNull
	@JsonProperty
	private final String type;

	@NotNull
	@JsonProperty
	private final ImmutableList<String> affectedServicesIds;

	@NotNull
	@JsonProperty
	private final LocalDateTime startTime;

	@NotNull
	@JsonProperty
	private final LocalDateTime createdAt;

	@NotNull
	@JsonProperty
	private final LocalDateTime updatedAt;

	@NotNull
	@JsonProperty
	private final ImmutableList<IncidentUpdate> incidentUpdates;

	public Incident withAffectedServicesIdsList(List<String> affectedServiceIds) {
		return withAffectedServicesIds(ImmutableList.copyOf(affectedServiceIds));
	}

	public Incident withIncidentUpdatesList(List<IncidentUpdate> incidentUpdates) {
		return withIncidentUpdates(ImmutableList.copyOf(incidentUpdates));
	}

	public Incident withAdditionalUpdate(IncidentUpdate newUpdate) {
		List<IncidentUpdate> updates = new ArrayList<>(incidentUpdates);
		updates.add(newUpdate);
		return new Incident(this.id, this.title, this.state, this.type, this.affectedServicesIds, this.startTime, this.createdAt, this.updatedAt, ImmutableList.copyOf(updates));
	}
}
