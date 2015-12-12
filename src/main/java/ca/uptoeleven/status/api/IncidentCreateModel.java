package ca.uptoeleven.status.api;

import ca.uptoeleven.status.core.IncidentType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import lombok.Value;
import lombok.experimental.Wither;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Value
@Wither
public class IncidentCreateModel {

	@NotEmpty(message = "Nope!")
	private final String title;

	@NotEmpty
	private final String description;

	@NotEmpty
	private final String state;

	@NotEmpty
	private final String type;

	@NotEmpty
	private final String serviceStatusId;

	private final List<String> affectedServiceIds;

	private final ZonedDateTime startTime;

	@JsonCreator
	public IncidentCreateModel(@JsonProperty(value = "title") final String title,
							   @JsonProperty(value = "description") final String description,
							   @JsonProperty(value = "state") final String state,
							   @JsonProperty(value = "type") final String type,
							   @JsonProperty(value = "serviceStatusId") final String serviceStatusId,
							   @JsonProperty(value = "affectedServiceIds") final List<String> affectedServiceIds,
							   @JsonProperty(value = "startTime") final ZonedDateTime startTime) {
		this.title = title;
		this.description = description;
		this.state = state;
		this.type = Strings.isNullOrEmpty(type) ? IncidentType.UNPLANNED : type;
		this.serviceStatusId = serviceStatusId;
		this.affectedServiceIds = affectedServiceIds;
		this.startTime = startTime;
	}

}
