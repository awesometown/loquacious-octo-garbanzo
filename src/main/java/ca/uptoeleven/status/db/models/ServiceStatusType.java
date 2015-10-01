package ca.uptoeleven.status.db.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ServiceStatusType {
    @NotNull
    @JsonProperty
    private String id;

    @NotNull
    @JsonProperty
    private String name;

    @NotNull
    @JsonProperty
    private String description;

    @NotNull
    @JsonProperty
    private int sortOrder;
}
