package ca.uptoeleven.status.api;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ServiceCreateModel {
    private String name;
    private String description;
}
