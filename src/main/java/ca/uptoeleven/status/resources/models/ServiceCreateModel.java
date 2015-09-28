package ca.uptoeleven.status.resources.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ServiceCreateModel {
    private String name;
    private String description;
}
