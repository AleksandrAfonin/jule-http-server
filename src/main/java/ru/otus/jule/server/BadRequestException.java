package ru.otus.jule.server;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }

}
