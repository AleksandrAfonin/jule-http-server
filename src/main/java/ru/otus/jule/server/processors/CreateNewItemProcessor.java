package ru.otus.jule.server.processors;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.jule.server.BadRequestException;
import ru.otus.jule.server.HttpRequest;
import ru.otus.jule.server.app.Item;
import ru.otus.jule.server.app.ItemsRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateNewItemProcessor implements RequestProcessor {
  private static final Logger logger = LogManager.getLogger(CreateNewItemProcessor.class.getName());
  private ItemsRepository itemsRepository;

  public CreateNewItemProcessor(ItemsRepository itemsRepository) {
    this.itemsRepository = itemsRepository;
  }

  @Override
  public void execute(HttpRequest request, OutputStream out) throws IOException {
    try {
      Gson gson = new Gson();
      Item item = itemsRepository.add(gson.fromJson(request.getBody(), Item.class));
      String itemJson = gson.toJson(item);
      String response = "" +
              "HTTP/1.1 201 Created\r\n" +
              "Content-Type: application/json\r\n" +
              "\r\n" +
              itemJson;
      out.write(response.getBytes(StandardCharsets.UTF_8));
      logger.info("Создан новый элемент");
    } catch (JsonParseException e) {
      e.printStackTrace();
      throw new BadRequestException("Некорректный формат входящего JSON объекта");
    }
  }
}