package org.example.springapp.utils.mapper;

import org.springframework.stereotype.Component;

@Component
public interface Mapper<E, D> {
    E toEntity(D dto);

}
