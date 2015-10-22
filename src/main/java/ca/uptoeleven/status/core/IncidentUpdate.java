package ca.uptoeleven.status.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Wither;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Wither
public class IncidentUpdate {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final String incidentId;

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
