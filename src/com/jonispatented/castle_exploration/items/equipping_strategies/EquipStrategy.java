package com.jonispatented.castle_exploration.items.equipping_strategies;

import com.jonispatented.castle_exploration.creatures.Inventory;
import com.jonispatented.castle_exploration.items.Item;

public interface EquipStrategy {

    void equip(Inventory inventory, Item itemToEquip);

}
