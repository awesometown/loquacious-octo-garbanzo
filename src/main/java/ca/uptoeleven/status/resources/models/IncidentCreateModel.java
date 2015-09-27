package ca.uptoeleven.status.resources.models;

import lombok.Data;

@Data
public class IncidentCreateModel {
    private IncidentUpdateCreateModel initialUpdate;
}
