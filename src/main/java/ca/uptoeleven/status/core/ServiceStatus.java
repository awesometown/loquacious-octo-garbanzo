package ca.uptoeleven.status.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ServiceStatus {

	@NotNull
	@JsonProperty
	private final String id;

	@NotNull
	@JsonProperty
	private final String name;

	@NotNull
	@JsonProperty
	private final String displayColor;

	@NotNull
	@JsonProperty
	private final int sortOrder;

	public static ServiceStatus OK = new ServiceStatus("ok", "Operational", "", 1);
	public static ServiceStatus DEGRADED = new ServiceStatus("degraded", "Degraded Performance", "", 2);
	public static ServiceStatus MINOR = new ServiceStatus("minor", "Partial Outage", "", 3);
	public static ServiceStatus MAJOR = new ServiceStatus("major", "Major Outage", "", 4);
	public static ServiceStatus MAINTENANCE = new ServiceStatus("maintenance", "Maintenance", "", 5);

	public static ServiceStatus getById(String id) {
		switch (id) {
			case "ok":
				return OK;
			case "degraded":
				return DEGRADED;
			case "minor":
				return MINOR;
			case "major":
				return MAJOR;
			case "maintenance":
				return MAINTENANCE;
			default:
				throw new IllegalArgumentException("Invalid serviceStatusId specified: " + id);
		}
	}

	public static boolean isValid(String id) {
		return id.matches("ok|degraded|minor|major|maintenance");
	}
}
