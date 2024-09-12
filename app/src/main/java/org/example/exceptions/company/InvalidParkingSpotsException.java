package org.example.exceptions.company;

public class InvalidParkingSpotsException extends RuntimeException {
    public InvalidParkingSpotsException(String message) {
        super(message);
    }
}
