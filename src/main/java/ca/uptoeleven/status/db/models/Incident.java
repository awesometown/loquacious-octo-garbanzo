package ca.uptoeleven.status.db.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Wither;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Wither
public class Incident {

    @JsonProperty
    private final String id;

    @NotNull
    @JsonProperty
    private final String title;

    @NotNull
    @JsonProperty
    private final String state;

    @NotNull
    @JsonProperty
    private final List<String> affectedServicesIds;

    @NotNull
    @JsonProperty
    private final LocalDateTime startTime;

    @NotNull
    @JsonProperty
    private final LocalDateTime createdAt;

    @NotNull
    @JsonProperty
    private final LocalDateTime updatedAt;
}
