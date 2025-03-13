package org.example.springapp.dto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private String serviceType;
    private List<Long> attractionsID;
}
