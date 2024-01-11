package com.jonispatented.castle_exploration.rooms;

import com.jonispatented.castle_exploration.items.Item;

import java.util.ArrayList;
import java.util.List;

public class SearchableArea {

    private final List<String> validNames;
    private final List<Item> items;

    private SearchableArea() {
        validNames = new ArrayList<>();
        items = new ArrayList<>();
    }

    public boolean isValidName(String name) {
        for (String validName : validNames)
            if (validName.equalsIgnoreCase(name))
                return true;
        return false;
    }

    public String getName() {
        return validNames.get(0);
    }

    public List<Item> getItems() {
        return items;
    }

    public void display() {
        System.out.println(getName().toUpperCase() + ':');
        if (items.isEmpty()) {
            System.out.println(" - No items found");
            return;
        }
        items.forEach(item -> System.out.println(" - " + item.getName()));
    }

    public static class Builder {

        private SearchableArea searchableArea;

        public Builder() {
            searchableArea = new SearchableArea();
        }

        public Builder addName(String name) {
            searchableArea.validNames.add(name);
            return this;
        }

        public Builder addItem(Item item) {
            searchableArea.items.add(item);
            return this;
        }

        public SearchableArea build() {
            if (searchableArea.validNames.isEmpty())
                searchableArea.validNames.add("DEFAULT");
            return searchableArea;
        }

    }

}
