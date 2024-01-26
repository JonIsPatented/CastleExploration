package com.jonispatented.castle_exploration.rooms;

import com.jonispatented.castle_exploration.engine.GameWindow;
import com.jonispatented.castle_exploration.items.Item;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
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

    public void display(GameWindow window) {
        StringBuilder displayStringBuilder = new StringBuilder(getName().toUpperCase() + ':');
        if (items.isEmpty())
            displayStringBuilder.append("\n - No items found");
        else
            items.forEach(item -> displayStringBuilder.append("\n - ").append(item.getName()));
        window.writeLineToGameOutput(displayStringBuilder.toString());
    }

    public static class Builder {

        public static SearchableArea buildFromJsonString(String json) throws ParseException {
            JSONObject areaJson = (JSONObject) new JSONParser().parse(json);
            SearchableArea.Builder builder = new SearchableArea.Builder();

            JSONArray names = (JSONArray) areaJson.get("names");
            for (Object name : names)
                builder.addName((String) name);

            JSONArray items = (JSONArray) areaJson.get("items");
            for (Object item : items)
                builder.addItem(Item.Builder.buildFromJsonString(((JSONObject) item).toJSONString()));

            return builder.build();
        }

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
