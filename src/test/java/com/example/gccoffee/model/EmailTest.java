package com.example.gccoffee.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            var email = new Email("accccc");
        });
    }

    @Test
    void testValidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            var email = new Email("hello@gmail.com");
            assertEquals("hello@gmail.com", email.getAddress());
        });
    }

    @Test
    void testEqEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            var email1 = new Email("hello@gmail.com");
            var email2 = new Email("hello@gmail.com");
            assertEquals(email1, email2);
        });
    }
}