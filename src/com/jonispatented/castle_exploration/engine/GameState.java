package com.jonispatented.castle_exploration.engine;

import com.jonispatented.castle_exploration.command_parsing.GameCommand;
import com.jonispatented.castle_exploration.creatures.player.Player;
import com.jonispatented.castle_exploration.items.Item;
import com.jonispatented.castle_exploration.rooms.Room;
import com.jonispatented.castle_exploration.rooms.SearchableArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameState {

    public static void loadGame(Engine gameContext) {
        gameContext.setCommandList(EXPLORATION_COMMANDS);

        List<Room> rooms = new ArrayList<>();
        rooms.add(
                new Room.Builder()
                        .addName("Throne Room")
                        .description("This regal grand hall is supported by granite pillars. " +
                                "At one end of the room sits a massive stone throne, braced with " +
                                "iron pegs and cold steel adornments. The room is almost unbearably " +
                                "cold, and your breath is visible in the air. In one corner stands " +
                                "a suspicious statue, and to the east is a door way leading into " +
                                "the library.")
                        .build()
        );
        rooms.add(
                new Room.Builder()
                        .addName("Library")
                        .description("The walls of this rooms are carved with recesses, books pushed into " +
                                "the stones of the castle, itself. What untold knowledge is buried in these " +
                                "tomes? How many thousands of hours of uninterrupted reading would it " +
                                "take even to scratch the surface?")
                        .addSearchableArea(new SearchableArea.Builder()
                                .addName("Bookshelf")
                                .addName("Shelf")
                                .addName("Bookshelves")
                                .addName("Shelves")
                                .addItem(new Item.Builder()
                                        .addName("Textbook")
                                        .addName("Book")
                                        .description("A book containing alchemical formulae. Its level seems best " +
                                                "suited as a teaching aid for university students or independent " +
                                                "study.")
                                        .build()
                                )
                                .build()
                        )
                        .build()
        );
        rooms.get(0).addExit("east", rooms.get(1));
        rooms.get(1).addExit("west", rooms.get(0));

        gameContext.getPlayer().setCurrentRoom(rooms.get(1));
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
