package org.example.springapp.utils.mapper;

import org.springframework.stereotype.Component;


/**
 * Интерфейс для преобразование Entity <E> и DTO <D>
 * @param <E> энтити
 * @param <D> дто
 */
@Component
public abstract class EntityMapper<E, D> {
    public abstract E toEntity(D dto);
}
