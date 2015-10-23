package ca.uptoeleven.status.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ca.uptoeleven.status.core.IdGenerator.newId;

@Data
@Wither
@AllArgsConstructor
public class Incident {

    public static Incident newIncident(String title, String firstUpdateDescription, String state, String serviceStatusId, List<String> affectedServicesIds, LocalDateTime timestamp) {
        IncidentUpdate update = new IncidentUpdate(newId(), firstUpdateDescription, state, serviceStatusId, timestamp, timestamp);
        return new Incident(
                newId(),
                title,
                state,
                ImmutableList.copyOf(affectedServicesIds),
                timestamp,
                timestamp,
                timestamp,
                ImmutableList.of(update));
    }

    public Incident(
            String id,
            String title,
            String state,
            List<String> affectedServicesIds,
            LocalDateTime startTime,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<IncidentUpdate> incidentUpdates) {
        this(id, title, state, ImmutableList.copyOf(affectedServicesIds), startTime, createdAt, updatedAt, ImmutableList.copyOf(incidentUpdates));
    }

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
    private final ImmutableList<String> affectedServicesIds;

    @NotNull
    @JsonProperty
    private final LocalDateTime startTime;

    @NotNull
    @JsonProperty
    private final LocalDateTime createdAt;

    @NotNull
    @JsonProperty
    private final LocalDateTime updatedAt;

    @NotNull
    @JsonProperty
    private final ImmutableList<IncidentUpdate> incidentUpdates;

    public Incident withAffectedServiceIds(List<String> affectedServiceIds) {
        return withAffectedServicesIds(ImmutableList.copyOf(affectedServiceIds));
    }

    public Incident withIncidentUpdates(List<IncidentUpdate> incidentUpdates) {
        return withIncidentUpdates(ImmutableList.copyOf(incidentUpdates));
    }

    public Incident withAdditionalUpdate(IncidentUpdate newUpdate) {
        List<IncidentUpdate> updates = new ArrayList<>(incidentUpdates);
        updates.add(newUpdate);
        return new Incident(this.id, this.title, this.state, this.affectedServicesIds, this.startTime, this.createdAt, this.updatedAt, ImmutableList.copyOf(updates));
    }
}
