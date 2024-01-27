package com.jonispatented.castle_exploration.creatures;

import com.jonispatented.castle_exploration.creatures.player.EquipSlot;
import com.jonispatented.castle_exploration.creatures.player.Player;
import com.jonispatented.castle_exploration.engine.Engine;
import com.jonispatented.castle_exploration.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Item> items;
    private final Player owner;
    private final EquipSlot mainHandSlot, offHandSlot, armorSlot;

    public Inventory(Player owner) {
        items = new ArrayList<>();
        this.owner = owner;
        mainHandSlot = new EquipSlot();
        offHandSlot = new EquipSlot();
        armorSlot = new EquipSlot();
    }

    public Player getOwner() {
        return owner;
    }

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public Item getItem(String itemName) {
        for (Item item : items)
            if (item.isValidName(itemName))
                return item;
        return null;
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public Item removeItem(int index) {
        return items.remove(index);
    }

    public EquipSlot getMainHandSlot() {
        return mainHandSlot;
    }

    public EquipSlot getOffHandSlot() {
        return offHandSlot;
    }

    public EquipSlot getArmorSlot() {
        return armorSlot;
    }

    public boolean isEquipped(Item item) {
        if (mainHandSlot.isEquipped(item))
            return true;
        if (offHandSlot.isEquipped(item))
            return true;
        if (armorSlot.isEquipped(item))
            return true;
        return false;
    }

    public boolean unequip(Item item) {
        boolean unequipSuccessful = false;
        if (mainHandSlot.unequip(item))
            unequipSuccessful = true;
        if (offHandSlot.unequip(item))
            unequipSuccessful = true;
        if (armorSlot.unequip(item))
            unequipSuccessful = true;
        return unequipSuccessful;
    }

    public boolean equip(String itemName) {
        Item itemToEquip = getItem(itemName);
        if (itemToEquip == null)
            return false;
        if (isEquipped(itemToEquip))
            return false;
        itemToEquip.equip(this);
        return true;
    }

    public String getInventoryString() {
        if (items.isEmpty())
            return "Inventory empty";
        StringBuilder inventoryStringBuilder = new StringBuilder();
        items.forEach(item -> inventoryStringBuilder
                .append(" - ").append(item.getName()).append(":\n")
                .append("   - ").append(item.getDescription()).append('\n'));
        inventoryStringBuilder.deleteCharAt(inventoryStringBuilder.length() - 1);
        return inventoryStringBuilder.toString();
    }

    public String getSimpleInventoryString() {
        if (items.isEmpty())
            return "Inventory empty";
        StringBuilder inventoryStringBuilder = new StringBuilder();
        items.forEach(item -> inventoryStringBuilder
                .append(" - ").append(item.getName()).append("\n"));
        inventoryStringBuilder.deleteCharAt(inventoryStringBuilder.length() - 1);
        return inventoryStringBuilder.toString();
    }

}
