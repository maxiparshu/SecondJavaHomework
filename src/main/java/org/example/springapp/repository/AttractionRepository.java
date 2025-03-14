package org.example.springapp.repository;

import org.example.springapp.model.Attraction;
import org.example.springapp.utils.enums.ServiceType;
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
     * Получить достопримечательность по её ID.
     *
     * @param id Идентификатор достопримечательности.
     * @return {@link Optional} с найденной достопримечательностью, если она существует.
     */
    Optional<Attraction> getAttractionById(Long id);

    /**
     * Найти достопримечательности по части названия.
     *
     * @param name Часть названия достопримечательности.
     * @return Список достопримечательностей, названия которых содержат указанный фрагмент, игнорируя регистр.
     */
    List<Attraction> findByNameContainingIgnoreCase(String name);

    /**
     * Найти достопримечательности по городу.
     *
     * @param city Город, в котором расположена достопримечательность.
     * @return Список достопримечательностей, расположенных в указанном городе.
     */
    List<Attraction> findByAddressCity(String city);

    /**
     * Найти достопримечательности по региону.
     *
     * @param region Регион, в котором расположена достопримечательность.
     * @return Список достопримечательностей, расположенных в указанном регионе.
     */
    List<Attraction> findByAddressRegion(String region);

    /**
     * Найти достопримечательности по типу услуги.
     *
     * @param serviceType Тип услуги, предоставляемой достопримечательностью.
     * @return Список достопримечательностей, которые предоставляют указанную услугу.
     */
    List<Attraction> findByServicesServiceType(ServiceType serviceType);
}
