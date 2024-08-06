package ru.otus.jule.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
  private static final Logger logger = LogManager.getLogger(HttpServer.class.getName());
  private int port;
  private Dispatcher dispatcher;

  public HttpServer(int port) {
    this.port = port;
    this.dispatcher = new Dispatcher();
  }

  public void start() {
    try (ServerSocket serverSocket = new ServerSocket(port);
         ExecutorService executorService = Executors.newFixedThreadPool(10)) {
      logger.info("Сервер запущен на порту: " + port);
      while (true) {
        try {
          Socket socket = serverSocket.accept();
          executorService.execute(new RequestHandler(socket, dispatcher));

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
