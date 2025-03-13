package org.example.springapp.utils.mapper;

import org.springframework.stereotype.Component;


/**
 * Интерфейс для преобразование Entity <E> и DTO <D>
 * @param <E> энтити
 * @param <D> дто
 */
@Component
public interface Mapper<E, D> {
    E toEntity(D dto);

}
