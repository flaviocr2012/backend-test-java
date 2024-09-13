package org.example.exceptions.vehicle;

public class VehicleAlreadyInsideException extends RuntimeException {
    public VehicleAlreadyInsideException(String message) {
        super(message);
    }
}
