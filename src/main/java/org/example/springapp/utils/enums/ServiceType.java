package org.example.springapp.utils.enums;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * Перечисление, представляющее типы усдуг.
 * <p>
 * Каждый тип достопримечательности имеет своё отображаемое название (displayName),
 * которое может быть использовано, например, для вывода на пользовательский интерфейс.
 * </p>
 */
@Getter
public enum ServiceType {
    GUIDE("Гид"),
    CAR_EXCURSION("Aвто_эксурсия"),
    MEALS("Питание");
    private final String displayName;
    /**
     * Конструктор для создания экземпляра перечисления с заданным отображаемым названием.
     *
     * @param displayName Название типа достопримечательности, которое будет отображаться.
     */
    ServiceType(String displayName) {
        this.displayName = displayName;
    }
    public static ServiceType fromDisplayName(String displayName) {
        return Stream.of(values())
                .filter(type -> type.getDisplayName().equalsIgnoreCase(displayName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown display name: " + displayName));
    }
}
