package org.example.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity

public class TicketInfo {
    @Id
    private Long id;

}
