package org.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.springapp.model.Service;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Service}.
 */
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    /**
     * Метод для получения {@link Service} по айди
     * @return  {@link Optional} для  {@link Service}
     */
    Optional<Service> getServiceById(Long id);
}
