package com.jonispatented.castle_exploration.items.equipping_strategies;

import com.jonispatented.castle_exploration.creatures.Inventory;
import com.jonispatented.castle_exploration.engine.GameWindow;
import com.jonispatented.castle_exploration.items.Item;

public class MainHandEquipStrategy implements EquipStrategy {

    @Override
    public void equip(Inventory inventory, Item itemToEquip) {
        GameWindow gameWindow = inventory.getOwner().getGameContext().getGameWindow();
        if (inventory.isEquipped(itemToEquip)) {
            gameWindow.writeLineToGameOutput(itemToEquip.getName() + " is already equipped.");
            return;
        }
        inventory.getMainHandSlot().equip(itemToEquip);
        gameWindow.writeLineToGameOutput("Equipped " + itemToEquip.getName() + '.');
    }

}
