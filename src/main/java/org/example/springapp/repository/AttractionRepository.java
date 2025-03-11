package org.example.springapp.repository;

import org.example.springapp.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
    Optional<Attraction> getAttractionById(@Param("id") Long id);
}
