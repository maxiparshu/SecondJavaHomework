package org.example.springapp.repository;

import org.example.springapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Address}.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    /**
     *  Метод для получения {@link Address} по айди
     * @return  {@link Optional} для  {@link Address}
     */
    Optional<Address> getAddressById(Long id);

}
