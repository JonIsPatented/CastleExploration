package com.jonispatented.castle_exploration.creatures.player;

import com.jonispatented.castle_exploration.creatures.Inventory;
import com.jonispatented.castle_exploration.creatures.Statistic;
import com.jonispatented.castle_exploration.engine.Engine;
import com.jonispatented.castle_exploration.rooms.Room;

public class Player {

    private Engine gameContext;
    private Room currentRoom;
    private Inventory inventory;
    private Statistic healthPoints, staminaPoints, manaPoints;

    public Player(Engine gameContext) {
        this.gameContext = gameContext;
        inventory = new Inventory(this);
        healthPoints = new Statistic(0, 24, 24);
        staminaPoints = new Statistic(0, 8, 8);
        manaPoints = new Statistic(0, 6, 6);
    }

    public Engine getGameContext() {
        return gameContext;
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

    public String getStatsString() {
        StringBuilder statsStringBuilder = new StringBuilder();

        statsStringBuilder.append("HEALTH: ")
                .append(healthPoints.getCurrent())
                .append("/")
                .append(healthPoints.getMax());

        statsStringBuilder.append("\n");

        statsStringBuilder.append("STAMINA: ")
                .append(staminaPoints.getCurrent())
                .append("/")
                .append(staminaPoints.getMax());

        statsStringBuilder.append("\n");

        statsStringBuilder.append("MANA: ")
                .append(manaPoints.getCurrent())
                .append("/")
                .append(manaPoints.getMax());

        statsStringBuilder.append("\n");

        statsStringBuilder.append("MAIN HAND: ");
        statsStringBuilder.append(inventory.getMainHandSlot().getItemName().toUpperCase());

        statsStringBuilder.append("\n");

        statsStringBuilder.append("OFF HAND: ");
        statsStringBuilder.append(inventory.getOffHandSlot().getItemName().toUpperCase());

        statsStringBuilder.append("\n");

        statsStringBuilder.append("ARMOR: ");
        statsStringBuilder.append(inventory.getArmorSlot().getItemName().toUpperCase());

        return statsStringBuilder.toString();
    }

}
