package com.jonispatented.castle_exploration.items;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private List<String> validNames;
    private String description;

    private Item() {
        validNames = new ArrayList<>();
        description = "DEFAULT";
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

    public String getDescription() {
        return description;
    }

    public static class Builder {

        private Item item;

        public Builder() {
            item = new Item();
        }

        public Builder addName(String name) {
            item.validNames.add(name);
            return this;
        }

        public Builder description(String description) {
            item.description = description;
            return this;
        }

        public Item build() {
            if (item.validNames.isEmpty())
                item.validNames.add("DEFAULT");
            return item;
        }

    }

}
