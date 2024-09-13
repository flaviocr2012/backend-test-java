package org.example.exceptions.vehicle;

public class VehicleNotInsideException extends RuntimeException {
    public VehicleNotInsideException(String message) {
        super(message);
    }
}
