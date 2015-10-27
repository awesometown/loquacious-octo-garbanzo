package ca.uptoeleven.status.api;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class IncidentUpdateViewModel {
    private final String id;
    private final String description;
    private final String state;
    private final String updatedBy;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;

}
