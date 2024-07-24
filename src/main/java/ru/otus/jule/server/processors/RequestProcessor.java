package ru.otus.jule.server.processors;

import ru.otus.jule.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestProcessor {
  void execute(HttpRequest request, OutputStream out) throws IOException;
}