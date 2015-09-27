package ca.uptoeleven.status.db.models;

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
}
