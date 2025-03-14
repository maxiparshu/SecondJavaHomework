package org.example.springapp.repository;

import org.example.springapp.model.TicketInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link TicketInfo}.
 */
@Repository
public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long> {
    /**
     * Метод для получения {@link TicketInfo} по айди
     *
     * @return {@link Optional} для  {@link TicketInfo}
     */
    Optional<TicketInfo> getTicketInfoById(Long id);

}
