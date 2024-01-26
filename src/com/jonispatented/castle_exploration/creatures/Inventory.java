package com.jonispatented.castle_exploration.creatures;

import com.jonispatented.castle_exploration.engine.GameWindow;
import com.jonispatented.castle_exploration.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public Item removeItem(int index) {
        return items.remove(index);
    }

    public void display(String inventoryTitle, GameWindow window) {
        StringBuilder inventoryStringBuilder = new StringBuilder(inventoryTitle);
        if (items.isEmpty()) {
            inventoryStringBuilder.append("\nInventory empty");
            return;
        }
        else
            items.forEach(item -> inventoryStringBuilder
                    .append("\n - ").append(item.getName()).append(":\n")
                    .append("   - ").append(item.getDescription()));
        window.writeLineToGameOutput(inventoryStringBuilder.toString());
    }

}
