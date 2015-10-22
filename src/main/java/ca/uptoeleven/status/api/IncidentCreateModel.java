package ca.uptoeleven.status.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Wither
@NoArgsConstructor
@AllArgsConstructor
public class IncidentCreateModel {

    @JsonProperty
    @NotEmpty(message="Nope!")
    private String title;

    @JsonProperty
    @NotEmpty
    private String description;

    @JsonProperty
    @NotEmpty
    private String state;

    @JsonProperty
    @NotEmpty
    private String serviceStatusId;

    @JsonProperty
    private List<String> affectedServiceIds;

    @JsonProperty
    private LocalDateTime startTime;
}
