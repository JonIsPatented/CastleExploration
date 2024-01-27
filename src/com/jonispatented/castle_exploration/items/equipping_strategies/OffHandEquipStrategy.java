package com.jonispatented.castle_exploration.items.equipping_strategies;

import com.jonispatented.castle_exploration.creatures.Inventory;
import com.jonispatented.castle_exploration.engine.Engine;
import com.jonispatented.castle_exploration.engine.GameWindow;
import com.jonispatented.castle_exploration.items.Item;

public class OffHandEquipStrategy implements EquipStrategy {

    @Override
    public void equip(Engine gameContext, Inventory inventory, Item itemToEquip) {
        GameWindow gameWindow = gameContext.getGameWindow();
        if (inventory.isEquipped(itemToEquip)) {
            gameWindow.writeLineToGameOutput(itemToEquip.getName() + " is already equipped.");
            return;
        }
        inventory.setOffHand(itemToEquip);
        gameWindow.writeLineToGameOutput("Equipped " + itemToEquip.getName() + '.');
    }

}
