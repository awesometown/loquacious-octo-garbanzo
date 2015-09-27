package ca.uptoeleven.status.db.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class Service {

    @NotNull
    @JsonProperty
    private final String id;

    @NotNull
    @JsonProperty
    private final String name;

    @NotNull
    @JsonProperty
    private final String description;

    @NotNull
    @JsonProperty
    private final String serviceStatusId;

    @NotNull
    @JsonProperty
    private final LocalDateTime createdAt;

    @NotNull
    @JsonProperty
    private final LocalDateTime updatedAt;
}
