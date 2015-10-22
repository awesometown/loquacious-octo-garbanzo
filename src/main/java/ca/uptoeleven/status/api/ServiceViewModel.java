package ca.uptoeleven.status.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceViewModel {
    private final String id;
    private final String name;
    private final String description;
    private final ServiceStatusViewModel serviceStatus;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
