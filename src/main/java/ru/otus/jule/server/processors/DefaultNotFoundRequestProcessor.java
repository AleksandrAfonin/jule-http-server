package ru.otus.jule.server.processors;

import ru.otus.jule.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DefaultNotFoundRequestProcessor implements RequestProcessor{
  @Override
  public void execute(HttpRequest request, OutputStream out) throws IOException {
    String response = "" +
            "HTTP/1.1 404 Not Found\r\n" +
            "Content-type: text/html\r\n" +
            "\r\n" +
            "<html><body><h1>Page Not Found</h1></body></html>";
    out.write(response.getBytes(StandardCharsets.UTF_8));
  }
}
