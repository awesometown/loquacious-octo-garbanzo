package ca.uptoeleven.status.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Wither;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ca.uptoeleven.status.core.IdGenerator.newId;
import static ca.uptoeleven.status.core.UtcDateTime.nowUtc;

@Data
@Wither
public class IncidentUpdate {

    public static IncidentUpdate createNew(String description, String state, String serviceStatusId) {
	    LocalDateTime now = nowUtc();
	    return new IncidentUpdate(newId(), description, state, serviceStatusId, now, now);
    }

    @JsonProperty
    private final String id;

    @NotNull
    @JsonProperty
    private final String description;

    @JsonProperty
    private final String newState;

    @JsonProperty
    private final String newServiceStatusId;

    @NotNull
    @JsonProperty
    private final LocalDateTime createdAt;

    @NotNull
    @JsonProperty
    private final LocalDateTime updatedAt;

}
