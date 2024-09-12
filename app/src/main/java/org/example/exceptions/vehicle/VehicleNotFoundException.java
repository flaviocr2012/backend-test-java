package org.example.exceptions.vehicle;

public class VehicleNotFoundException extends RuntimeException {
  public VehicleNotFoundException(String message) {
    super(message);
  }
}
