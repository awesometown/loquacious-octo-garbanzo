package ca.uptoeleven.status.db.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class IncidentUpdate {
    @NotNull
    @JsonProperty
    private final String id;

    @NotNull
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
