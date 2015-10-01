package ca.uptoeleven.status.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
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
