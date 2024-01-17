package com.jonispatented.castle_exploration.items;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Item {

    private final List<String> validNames;
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

        private static Item.Builder builderFromJsonFile(String fileName) throws ParseException {
            try {
                return builderFromJsonString(((JSONObject) new JSONParser()
                        .parse(new FileReader("res/items/" + fileName + ".json"))).toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
                return new Item.Builder();
            }
        }

        private static Item.Builder builderFromJsonString(String json) throws ParseException {
            JSONObject itemJson = (JSONObject) new JSONParser().parse(json);
            Item.Builder builder;

            if (itemJson.containsKey("base"))
                builder = builderFromJsonFile((String) itemJson.get("base"));
            else builder = new Item.Builder();

            if (itemJson.containsKey("description"))
                builder.description((String) itemJson.get("description"));

            if (itemJson.containsKey("names")) {
                JSONArray names = (JSONArray) itemJson.get("names");
                for (Object name : names)
                    builder.addName((String) name);
            }

            return builder;
        }

        public static Item buildFromJsonString(String json) throws ParseException {
            return builderFromJsonString(json).build();
        }

        private final Item item;

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
