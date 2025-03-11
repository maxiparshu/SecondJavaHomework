package org.example.springapp.model;

import lombok.Getter;

@Getter
public enum AttractionType{
    PALACE("Дворец"),
    PARK("Парк"),
    MUSEUM("Музей"),
    ARCHAEOLOGICAL_SITE("Археологический объект"),
    RESERVE("Заповедник");

    private final String displayName;
    AttractionType(String displayName) {
        this.displayName = displayName;
    }

}
