package org.example.springapp.utils.enums;

import lombok.Getter;

/**
 * Перечисление, представляющее типы достопримечательностей.
 * <p>
 * Каждый тип достопримечательности имеет своё отображаемое название (displayName),
 * которое может быть использовано, например, для вывода на пользовательский интерфейс.
 * </p>
 */
@Getter
public enum AttractionType{
    PALACE("Дворец"),
    PARK("Парк"),
    MUSEUM("Музей"),
    ARCHAEOLOGICAL_SITE("Археологический объект"),
    RESERVE("Заповедник");

    private final String displayName;
    /**
     * Конструктор для создания экземпляра перечисления с заданным отображаемым названием.
     *
     * @param displayName Название типа достопримечательности, которое будет отображаться.
     */
    AttractionType(String displayName) {
        this.displayName = displayName;
    }

}
