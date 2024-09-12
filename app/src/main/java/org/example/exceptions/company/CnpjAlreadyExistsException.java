package org.example.exceptions.company;

public class CnpjAlreadyExistsException extends RuntimeException {
  public CnpjAlreadyExistsException(String message) {
    super(message);
  }
}
