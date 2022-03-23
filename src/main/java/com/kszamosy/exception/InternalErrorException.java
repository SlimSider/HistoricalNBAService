package com.kszamosy.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message, Throwable t) {super(message, t);}
}
