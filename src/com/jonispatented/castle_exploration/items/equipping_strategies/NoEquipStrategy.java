package com.jonispatented.castle_exploration.items.equipping_strategies;

import com.jonispatented.castle_exploration.creatures.Inventory;
import com.jonispatented.castle_exploration.engine.Engine;
import com.jonispatented.castle_exploration.engine.GameWindow;
import com.jonispatented.castle_exploration.items.Item;

public class NoEquipStrategy implements EquipStrategy {

    @Override
    public void equip(Inventory inventory, Item itemToEquip) {
        inventory.getOwner().getGameContext().getGameWindow()
                .writeLineToGameOutput("Cannot equip " + itemToEquip.getName() + '.');
    }

}
