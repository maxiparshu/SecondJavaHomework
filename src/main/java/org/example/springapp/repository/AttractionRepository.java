package org.example.springapp.repository;

import org.example.springapp.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Репозиторий для работы с сущностями {@link Attraction}.
 */
@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    /**
     * Метод для получения {@link Attraction} по айди
     * @return  {@link Optional} для  {@link Attraction}
     */
    Optional<Attraction> getAttractionById(Long id);
    List<Attraction> findByNameContainingIgnoreCase(String name);
    List<Attraction> findByAddressCity(String city);

    // Поиск по региону
    List<Attraction> findByAddressRegion(String region);
}
