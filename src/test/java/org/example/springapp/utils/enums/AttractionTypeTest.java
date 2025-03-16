package org.example.springapp.utils.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttractionTypeTest {

    @Test
    void testGetDisplayName() {
        assertEquals("Дворец", AttractionType.PALACE.getDisplayName());
        assertEquals("Парк", AttractionType.PARK.getDisplayName());
    }

    @Test
    void testFromDisplayNameValid() {
        assertEquals(AttractionType.MUSEUM, AttractionType.fromDisplayName("Музей"));
        assertEquals(AttractionType.TEMPLE, AttractionType.fromDisplayName("Храм"));
    }

    @Test
    void testFromDisplayNameIgnoreCase() {
        assertEquals(AttractionType.PARK, AttractionType.fromDisplayName("парк"));
        assertEquals(AttractionType.ARCHAEOLOGICAL_SITE, AttractionType.fromDisplayName("аРхеОлогИческий объект"));
    }

    @Test
    void testFromDisplayNameInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                AttractionType.fromDisplayName("Неизвестное место"));

        assertEquals("Unknown display name: Неизвестное место", exception.getMessage());
    }
}