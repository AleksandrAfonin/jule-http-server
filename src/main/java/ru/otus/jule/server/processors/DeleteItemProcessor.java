package ru.otus.jule.server.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.jule.server.BadRequestException;
import ru.otus.jule.server.HttpRequest;
import ru.otus.jule.server.app.ItemsRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DeleteItemProcessor implements RequestProcessor {
  private static final Logger logger = LogManager.getLogger(DeleteItemProcessor.class.getName());
  private ItemsRepository itemsRepository;

  public DeleteItemProcessor(ItemsRepository itemsRepository) {
    this.itemsRepository = itemsRepository;
  }

  @Override
  public void execute(HttpRequest request, OutputStream out) throws IOException {
    if (!request.containsParameter("id")) {
      throw new BadRequestException("Parameter 'id' is missing");
    }
    long id;
    try {
      id = Long.parseLong(request.getParameter("id"));
    } catch (NumberFormatException e) {
      throw new BadRequestException("Parameter 'id' has incorrect type");
    }
    String response;
    if (itemsRepository.delete(id)) {
      response = "" +
              "HTTP/1.1 200 OK\r\n" +
              "\r\n";
      logger.info("Элемент с id = " + id + " успешно удален");
    } else {
      response = "" +
              "HTTP/1.1 204 No Content\r\n" +
              "\r\n";
      logger.info("Элемент с id = " + id + " не существует");
    }
    out.write(response.getBytes(StandardCharsets.UTF_8));
  }
}
