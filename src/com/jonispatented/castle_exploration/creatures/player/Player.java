package com.jonispatented.castle_exploration.creatures.player;

import com.jonispatented.castle_exploration.creatures.Inventory;
import com.jonispatented.castle_exploration.rooms.Room;

public class Player {

    private Room currentRoom;
    private Inventory inventory;

    public Player() {
        inventory = new Inventory();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    public Inventory getInventory() {
        return inventory;
    }

}
