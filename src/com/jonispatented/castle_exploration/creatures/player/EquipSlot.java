package com.jonispatented.castle_exploration.creatures.player;

import com.jonispatented.castle_exploration.items.Item;

public class EquipSlot {

    private Item item;

    public Item getItem() {
        return item;
    }

    public String getItemName() {
        if (item == null)
            return "None";
        return item.getName();
    }

    public boolean isEmpty() {
        return item == null;
    }

    public void equip(Item item) {
        this.item = item;
    }

    public boolean unequip() {
        if (item == null)
            return false;
        item = null;
        return true;
    }

    public boolean isEquipped(Item item) {
        return this.item == item;
    }

}
