package org.example.springapp.utils.enums;

import lombok.Getter;

@Getter
public enum ServiceType {

    GUIDE("Гид"),
    CAR_EXCURSION("Aвто_эксурсия"),
    MEALS("Питание");
    private final String displayName;
    ServiceType(String displayName) {
        this.displayName = displayName;
    }

}
