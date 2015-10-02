package ca.uptoeleven.status.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class IncidentCreateModel {

    @JsonProperty
    private String title;

    @JsonProperty
    private String description;

    @JsonProperty
    private String state;

    @JsonProperty
    private String serviceStatusId;

    @JsonProperty
    private List<String> affectedServiceIds;

    @JsonProperty
    LocalDateTime startTime;
}
