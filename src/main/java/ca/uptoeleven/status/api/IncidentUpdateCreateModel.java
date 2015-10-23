package ca.uptoeleven.status.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Getter
@Wither
@NoArgsConstructor
@AllArgsConstructor
public class IncidentUpdateCreateModel {
    private String description;
    private String state;
    private String serviceStatusId;
}
