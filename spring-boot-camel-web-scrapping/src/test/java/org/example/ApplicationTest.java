package org.example;

import org.junit.jupiter.api.Test;

import static org.example.Application.main;
import static org.junit.Assert.assertThrows;

class ApplicationTest {

    @Test
    void mainTest() {
        assertThrows(IllegalArgumentException.class, () -> main(null));
    }
}
