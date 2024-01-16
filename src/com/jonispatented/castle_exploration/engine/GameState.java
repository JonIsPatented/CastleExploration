package com.jonispatented.castle_exploration.engine;

import com.jonispatented.castle_exploration.command_parsing.GameCommand;
import com.jonispatented.castle_exploration.creatures.player.Player;
import com.jonispatented.castle_exploration.items.Item;
import com.jonispatented.castle_exploration.rooms.Room;
import com.jonispatented.castle_exploration.rooms.SearchableArea;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameState {

    public static void loadGame(Engine gameContext) {
        gameContext.setCommandList(EXPLORATION_COMMANDS);

        List<Room> rooms = new ArrayList<>();

        rooms.add(loadRoomFromJsonFile("res/rooms/throne_room.json"));
        rooms.add(loadRoomFromJsonFile("res/rooms/castle_library.json"));

        rooms.get(0).addExit("east", rooms.get(1));
        rooms.get(1).addExit("west", rooms.get(0));

        gameContext.getPlayer().setCurrentRoom(rooms.get(1));
    }

    private static Room loadRoomFromJsonFile(String file) {
        try {
            return Room.Builder.buildFromJsonString(((JSONObject) new JSONParser()
                    .parse(new FileReader(file))).toJSONString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new Room.Builder().build();
        }
    }

    public static void saveGame(Engine gameContext) {
        // TODO
    }

    public static final List<GameCommand> EXPLORATION_COMMANDS = Arrays.asList(
            new GameCommand(
                    "[go,head] <direction>",
                    (keyTerms, gameContext) -> {
                        Player player = gameContext.getPlayer();
                        Room toRoom = player.getCurrentRoom().getExit(keyTerms.get(0));
                        if (toRoom == null) {
                            System.out.println("You can't go in that direction.");
                            return;
                        }
                        player.setCurrentRoom(toRoom);
                        System.out.println(player.getCurrentRoom().getName().toUpperCase());
                        System.out.println(player.getCurrentRoom().getDescription());
                    }
            ),
            new GameCommand(
                    "[go,head] [to,into] (the) <location>",
                    (keyTerms, gameContext) -> {
                        Player player = gameContext.getPlayer();
                        Room toRoom = null;

                        String[] directions = {
                                "north",
                                "east",
                                "south",
                                "west"
                        };
                        for (String direction : directions) {
                            Room currentExit = player.getCurrentRoom().getExit(direction);
                            if (currentExit == null) continue;
                            if (currentExit.isValidName(keyTerms.get(0))) {
                                toRoom = currentExit;
                                break;
                            }
                        }

                        if (toRoom == null) {
                            System.out.println("You can't go in that direction.");
                            return;
                        }
                        player.setCurrentRoom(toRoom);
                        System.out.println(player.getCurrentRoom().getName().toUpperCase());
                        System.out.println(player.getCurrentRoom().getDescription());
                    }
            ),
            new GameCommand(
                    "[look] (around)",
                    (keyTerms, gameContext) -> System.out.println(gameContext.getPlayer().getCurrentRoom().getDescription())
            ),
            new GameCommand(
                    "[look_around] (the) [room]",
                    (keyTerms, gameContext) -> System.out.println(gameContext.getPlayer().getCurrentRoom().getDescription())
            ),
            new GameCommand(
                    "[pick_up,grab,take] (a,the) <item>",
                    (keyTerms, gameContext) -> {
                        Player player = gameContext.getPlayer();
                        Room room = player.getCurrentRoom();
                        String itemName = keyTerms.get(0);

                        for (Item item : room.getItems()) {
                            if (!item.isValidName(itemName))
                                continue;
                            room.getItems().remove(item);
                            room.getSearchableAreas().forEach(area -> area.getItems().remove(item));
                            player.getInventory().addItem(item);
                            System.out.println("Picked up the " + item.getName() + '.');
                            return;
                        }

                        System.out.println("Could not find an item called \"" + itemName + "\" here.");
                    }
            ),
            new GameCommand(
                    "[pick_up,grab,take] (a,the) <item> [from,by,off,off_of] (a,the) <location>",
                    (keyTerms, gameContext) -> System.out.println("GRAB 2 CHOSEN:\n" + keyTerms.get(0) + '\n' + keyTerms.get(1))
            ),
            new GameCommand(
                    "[search,explore] (a,the) <location>",
                    (keyTerms, gameContext) -> {
                        Room currentRoom = gameContext.getPlayer().getCurrentRoom();
                        List<SearchableArea> searchableAreas = currentRoom.getSearchableAreas();
                        String areaName = keyTerms.get(0);

                        for (SearchableArea area : searchableAreas) {
                            if (!area.isValidName(areaName))
                                continue;
                            area.display();
                            currentRoom.getItems().addAll(area.getItems());
                            return;
                        }

                        System.out.println("Could not find an area called \"" + areaName + "\" here.");
                    }
            ),
            new GameCommand(
                    "(show) [inv,inventory]",
                    (keyTerms, gameContext) -> {
                        System.out.println("Inventory:");
                        gameContext.getPlayer().getInventory().display();
                    }
            ),
            new GameCommand(
                    "[quit,stop,exit] (the) [game]",
                    (keyTerms, gameContext) -> {
                        System.out.println("Goodbye!");
                        gameContext.stop();
                    }
            )
    );

}