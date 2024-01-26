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

    public String getInventoryString() {
        if (items.isEmpty())
            return "Inventory empty";
        StringBuilder inventoryStringBuilder = new StringBuilder();
        items.forEach(item -> inventoryStringBuilder
                .append(" - ").append(item.getName()).append(":\n")
                .append("   - ").append(item.getDescription()).append('\n'));
        inventoryStringBuilder.deleteCharAt(inventoryStringBuilder.length() - 1);
        return inventoryStringBuilder.toString();
    }

    public String getSimpleInventoryString() {
        if (items.isEmpty())
            return "Inventory empty";
        StringBuilder inventoryStringBuilder = new StringBuilder();
        items.forEach(item -> inventoryStringBuilder
                .append(" - ").append(item.getName()).append("\n"));
        inventoryStringBuilder.deleteCharAt(inventoryStringBuilder.length() - 1);
        return inventoryStringBuilder.toString();
    }

}
