package ru.otus.jule.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
  private Socket socket;
  private Dispatcher dispatcher;

  public RequestHandler(Socket socket, Dispatcher dispatcher) throws IOException {
    this.socket = socket;
    this.dispatcher = dispatcher;
  }

  @Override
  public void run() {
    try (InputStream in = socket.getInputStream();
         OutputStream out = socket.getOutputStream()) {
      byte[] buffer = new byte[8192];
      int n = in.read(buffer);
      if (n < 0) {
        System.out.println("Пустой запрос");
        return;
      }
      String rawRequest = new String(buffer, 0, n);
      HttpRequest request = new HttpRequest(rawRequest);
      request.printInfo(true);
      dispatcher.execute(request, out);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}