package com.jonispatented.castle_exploration.creatures.player;

import com.jonispatented.castle_exploration.creatures.Inventory;
import com.jonispatented.castle_exploration.creatures.Statistic;
import com.jonispatented.castle_exploration.rooms.Room;

public class Player {

    private Room currentRoom;
    private Inventory inventory;
    private Statistic healthPoints, staminaPoints, manaPoints;

    public Player() {
        inventory = new Inventory();
        healthPoints = new Statistic(0, 24, 24);
        staminaPoints = new Statistic(0, 8, 8);
        manaPoints = new Statistic(0, 6, 6);
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
        if (inventory.getMainHand() != null)
            statsStringBuilder.append(inventory.getMainHand().getName().toUpperCase());
        else
            statsStringBuilder.append("NONE");

        statsStringBuilder.append("\n");

        statsStringBuilder.append("OFF HAND: ");
        if (inventory.getOffHand() != null)
            statsStringBuilder.append(inventory.getOffHand().getName().toUpperCase());
        else
            statsStringBuilder.append("NONE");

        statsStringBuilder.append("\n");

        statsStringBuilder.append("ARMOR: ");
        if (inventory.getArmor() != null)
            statsStringBuilder.append(inventory.getArmor().getName().toUpperCase());
        else
            statsStringBuilder.append("NONE");

        return statsStringBuilder.toString();
    }

}
