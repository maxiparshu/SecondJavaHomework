package org.example.springapp.repository;


import org.example.springapp.model.Address;
import org.example.springapp.model.Attraction;
import org.example.springapp.model.Service;
import org.example.springapp.model.TicketInfo;
import org.example.springapp.utils.enums.ServiceType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("tourism_test")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("init.sql");

    @BeforeAll
    static void setup() {
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());

        System.setProperty("spring.liquibase.enabled", "true");
        System.setProperty("spring.liquibase.default_schema", "tourism");
        System.setProperty("spring.liquibase.url", postgres.getJdbcUrl());
        System.setProperty("spring.liquibase.user", postgres.getUsername());
        System.setProperty("spring.liquibase.password", postgres.getPassword());
    }

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TicketInfoRepository ticketInfoRepository;

    @Test
    void testAddressRepository() {
        var address = Address.builder().city("Springfield").build();
        var savedAddress = addressRepository.save(address);

        var foundAddress = addressRepository.getAddressById(savedAddress.getId());
        assertThat(foundAddress).isPresent();
        assertThat(foundAddress.get().getCity()).isEqualTo("Springfield");
    }

    @Test
    void testAttractionRepository() {
        var attraction = Attraction.builder().name("wonder world").build();
        var savedAttraction = attractionRepository.save(attraction);

        var foundAttractions = attractionRepository.findByNameContainingIgnoreCase("wonder");
        assertThat(foundAttractions).isNotEmpty().contains(savedAttraction);
    }

    @Test
    void testServiceRepository() {
        var service = Service.builder().serviceType(ServiceType.GUIDE).name("TEST").build();
        var savedService = serviceRepository.save(service);

        var foundService = serviceRepository.getServiceById(savedService.getId());
        assertThat(foundService).isPresent();
        assertThat(foundService.get().getServiceType()).isEqualTo(ServiceType.GUIDE);
    }

    @Test
    void testTicketInfoRepository() {
        var ticketInfo = TicketInfo.builder().currency("USD").price(BigDecimal.valueOf(15.0)).build();
        var savedTicketInfo = ticketInfoRepository.save(ticketInfo);

        var foundTicket = ticketInfoRepository.getTicketInfoById(savedTicketInfo.getId());
        assertThat(foundTicket).isPresent();
        assertThat(foundTicket.get().getCurrency()).isEqualTo("USD");
    }

}
