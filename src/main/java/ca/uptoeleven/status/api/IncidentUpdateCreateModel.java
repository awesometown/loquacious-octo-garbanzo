package ca.uptoeleven.status.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import javax.validation.constraints.NotNull;

@Getter
@Wither
@NoArgsConstructor
@AllArgsConstructor
public class IncidentUpdateCreateModel {
    @NotNull
    private String description;

    @NotNull
    private String state;

    @NotNull
    private String serviceStatusId;
}
