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

    public void display(GameWindow window) {
        if (items.isEmpty()) {
            window.writeLineToGameOutput("Inventory empty");
            return;
        }
        items.forEach(item -> window.writeLineToGameOutput(" - " + item.getName() + ":\n" +
                "   - " + item.getDescription()));
    }

}
