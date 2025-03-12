package org.example.springapp.dto;
import lombok.Builder;
import lombok.Data;
import org.example.springapp.utils.enums.ServiceType;

import java.util.List;

@Data
@Builder
public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private ServiceType serviceType;
    private List<Long> attractionsID;
}
