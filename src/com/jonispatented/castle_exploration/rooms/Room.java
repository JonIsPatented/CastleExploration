package com.jonispatented.castle_exploration.rooms;

import com.jonispatented.castle_exploration.items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {

    private final List<String> validNames;
    private String description;
    private final Map<String, Room> exits;

    private final List<Item> items;
    private final List<SearchableArea> searchableAreas;

    private Room() {
        validNames = new ArrayList<>();
        description = "DEFAULT";
        exits = new HashMap<>();
        items = new ArrayList<>();
        searchableAreas = new ArrayList<>();
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

    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    public void addExit(String direction, Room toNode) {
        exits.put(direction.toLowerCase(), toNode);
    }

    public List<Item> getItems() {
        return items;
    }

    public List<SearchableArea> getSearchableAreas() {
        return searchableAreas;
    }

    public static class Builder {

        private final Room room;

        public Builder() {
            room = new Room();
        }

        public Builder addName(String name) {
            room.validNames.add(name);
            return this;
        }

        public Builder description(String description) {
            room.description = description;
            return this;
        }

        public Builder addExit(String direction, Room toRoom) {
            room.exits.put(direction.toLowerCase(), toRoom);
            return this;
        }

        public Builder addSearchableArea(SearchableArea searchableArea) {
            room.searchableAreas.add(searchableArea);
            return this;
        }

        public Builder addItem(Item item) {
            room.items.add(item);
            return this;
        }

        public Room build() {
            if (room.validNames.isEmpty())
                room.validNames.add("DEFAULT");
            return room;
        }

    }

}
