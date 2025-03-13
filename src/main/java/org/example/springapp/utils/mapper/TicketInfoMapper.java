package org.example.springapp.utils.mapper;

import org.example.springapp.dto.TicketInfoDTO;
import org.example.springapp.model.TicketInfo;
import org.springframework.stereotype.Component;

@Component
public class TicketInfoMapper implements Mapper<TicketInfo, TicketInfoDTO> {
    @Override
    public TicketInfo toEntity(TicketInfoDTO dto) {
        return TicketInfo.builder()
                .id(dto.getId())
                .price(dto.getPrice())
                .availability(dto.getAvailability())
                .currency(dto.getCurrency())
                .build();
    }
}
