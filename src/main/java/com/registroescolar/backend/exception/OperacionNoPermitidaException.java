package com.registroescolar.backend.exception;

public class OperacionNoPermitidaException extends RuntimeException{
    public OperacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}