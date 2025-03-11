package org.example.springapp.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.springapp.utils.enums.ServiceType;

import java.util.List;

@Getter
@Setter
@Builder
public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private ServiceType serviceType;
    private List<AttractionDTO> attractions;
}
