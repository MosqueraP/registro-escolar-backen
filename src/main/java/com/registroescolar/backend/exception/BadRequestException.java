package com.registroescolar.backend.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String mensaje) {
        super(mensaje);
    }
}