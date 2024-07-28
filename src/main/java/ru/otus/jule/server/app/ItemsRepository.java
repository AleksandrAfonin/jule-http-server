package ru.otus.jule.server.app;

import java.math.BigDecimal;
import java.util.*;

public class ItemsRepository {
  private List<Item> items;

  public List<Item> getItems() {
    return Collections.unmodifiableList(items);
  }

  public ItemsRepository() {
    this.items = new ArrayList<>(Arrays.asList(
            new Item(1L, "Milk", BigDecimal.valueOf(80)),
            new Item(2L, "Bread", BigDecimal.valueOf(32)),
            new Item(3L, "Cheese", BigDecimal.valueOf(320))
    ));
  }

  public Item add(Item item) {
    Long newId = items.stream().mapToLong(Item::getId).max().orElse(0L) + 1L;
    item.setId(newId);
    items.add(item);
    return item;
  }

  public boolean delete(Long id){
    for (Item item : items){
      if (Objects.equals(item.getId(), id)){
        items.remove(item);
        return true;
      }
    }
    return false;
  }
}
