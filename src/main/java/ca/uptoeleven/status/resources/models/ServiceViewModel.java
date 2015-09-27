package ca.uptoeleven.status.resources.models;

import lombok.Data;

@Data
public class ServiceViewModel {
    private final String id;
    private final String name;
    private final String description;
}
