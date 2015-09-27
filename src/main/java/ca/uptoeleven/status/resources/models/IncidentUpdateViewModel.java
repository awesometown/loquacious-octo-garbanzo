package ca.uptoeleven.status.resources.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IncidentUpdateViewModel {
    private final String id;
    private final String state;
    private final String updatedBy;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

}
