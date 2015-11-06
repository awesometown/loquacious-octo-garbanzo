package ca.uptoeleven.status.api;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ServiceViewModel {
    private final String id;
    private final String name;
    private final String description;
    private final ServiceStatusViewModel serviceStatus;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
}
