package org.example.springapp.utils.mapper;

import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.service.CRUDService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс создает список энтити E из списка
 */
public class EntityByIDMapper {
    private EntityByIDMapper() {
    }

    /**
     *
     * @param ids список айди энтити {@link  E}
     * @param service сервис для возвращаемго {@link  E}
     * @param classEName имя энтити {@link  E}
     * @return список энтити {@link  E}
     * @param <E> возвращаемое энтити
     * @throws ResourceNotFoundException если хотя бы 1 айдишник не сущетвует
     */
    public static <E> List<E> fetchByIds(List<Long> ids, CRUDService<E> service, String classEName)
            throws ResourceNotFoundException {
        List<Long> failedIds = new ArrayList<>();

        List<E> entityList = ids.stream()
                .map(id -> {
                    try {
                        return service.getByID(id);
                    } catch (ResourceNotFoundException e) {
                        failedIds.add(id);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        if (!failedIds.isEmpty()) {
            throw new ResourceNotFoundException(classEName + "s with these IDs don't exist: " + failedIds);
        }

        return entityList;
    }
}
