package com.jonispatented.castle_exploration.engine;

import com.jonispatented.castle_exploration.command_parsing.GameCommand;
import com.jonispatented.castle_exploration.creatures.player.Player;
import com.jonispatented.castle_exploration.items.Item;
import com.jonispatented.castle_exploration.rooms.Room;
import com.jonispatented.castle_exploration.rooms.SearchableArea;

import java.util.*;

public class GameState {

    public static void loadGame(Engine gameContext) {
        gameContext.setCommandList(EXPLORATION_COMMANDS);

        List<Room> rooms = Room.Builder.buildRoomsFromJsonFiles();

        gameContext.getPlayer().setCurrentRoom(rooms.stream()
                .filter(room -> room.isValidName("Library")).findFirst().get());
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
                            gameContext.getGameWindow()
                                    .writeLineToGameOutput("You can't go in that direction.");
                            return;
                        }
                        player.setCurrentRoom(toRoom);
                        gameContext.getGameWindow()
                                .writeLineToGameOutput(player.getCurrentRoom().getName().toUpperCase());
                        gameContext.getGameWindow()
                                .writeLineToGameOutput(player.getCurrentRoom().getDescription());
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
                            gameContext.getGameWindow()
                                    .writeLineToGameOutput("You can't go in that direction.");
                            return;
                        }
                        player.setCurrentRoom(toRoom);
                        gameContext.getGameWindow()
                                .writeLineToGameOutput(player.getCurrentRoom().getName().toUpperCase());
                        gameContext.getGameWindow()
                                .writeLineToGameOutput(player.getCurrentRoom().getDescription());
                    }
            ),
            new GameCommand(
                    "[look] (around)",
                    (keyTerms, gameContext) -> gameContext.getGameWindow()
                            .writeLineToGameOutput(gameContext.getPlayer().getCurrentRoom().getDescription())
            ),
            new GameCommand(
                    "[look_around] (the) [room]",
                    (keyTerms, gameContext) -> gameContext.getGameWindow()
                            .writeLineToGameOutput(gameContext.getPlayer().getCurrentRoom().getDescription())
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
                            gameContext.getGameWindow().writeLineToGameOutput("Picked up the " + item.getName() + '.');
                            return;
                        }

                        gameContext.getGameWindow()
                                .writeLineToGameOutput("Could not find an item called \"" + itemName + "\" here.");
                    }
            ),
            new GameCommand(
                    "[pick_up,grab,take] (a,the) <item> [from,by,off,off_of] (a,the) <location>",
                    (keyTerms, gameContext) -> gameContext.getGameWindow()
                            .writeLineToGameOutput("GRAB 2 CHOSEN:\n" + keyTerms.get(0) + '\n' + keyTerms.get(1))
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

                        gameContext.getGameWindow()
                                .writeLineToGameOutput("Could not find an area called \"" + areaName + "\" here.");
                    }
            ),
            new GameCommand(
                    "(show) [inv,inventory]",
                    (keyTerms, gameContext) -> {
                        gameContext.getGameWindow().writeLineToGameOutput("Inventory:");
                        gameContext.getPlayer().getInventory().display(gameContext.getGameWindow());
                    }
            ),
            new GameCommand(
                    "[quit,stop,exit] (the) [game]",
                    (keyTerms, gameContext) -> {
                        gameContext.getGameWindow().writeLineToGameOutput("Goodbye!");
                        gameContext.stop();
                    }
            )
    );

}
