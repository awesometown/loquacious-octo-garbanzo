package ca.uptoeleven.status.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IncidentUpdateViewModel {
    private final String id;
    private final String description;
    private final String state;
    private final String updatedBy;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}
