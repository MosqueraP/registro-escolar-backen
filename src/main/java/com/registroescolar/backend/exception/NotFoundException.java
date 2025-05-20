package com.registroescolar.backend.exception;

public class NotFoundException extends RuntimeException {

    // lanzar exepción al no encontrar un estudiante, curso, etc
    public NotFoundException(String message) {
        super(message);
    }

}
