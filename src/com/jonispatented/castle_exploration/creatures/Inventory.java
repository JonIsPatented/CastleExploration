package com.jonispatented.castle_exploration.creatures;

import com.jonispatented.castle_exploration.engine.Engine;
import com.jonispatented.castle_exploration.engine.GameWindow;
import com.jonispatented.castle_exploration.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Item> items;

    private Item mainHand, offHand, armor;

    public Inventory() {
        items = new ArrayList<>();
        mainHand = null;
        offHand = null;
        armor = null;
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
        if (isEquipped(item))
            unequip(item);
        return items.remove(item);
    }

    public Item removeItem(int index) {
        Item itemToRemove = items.remove(index);
        if (isEquipped(itemToRemove))
            unequip(itemToRemove);
        return itemToRemove;
    }

    public Item getMainHand() {
        return mainHand;
    }

    public Item getOffHand() {
        return offHand;
    }

    public Item getArmor() {
        return armor;
    }

    public void setMainHand(Item mainHand) {
        this.mainHand = mainHand;
    }

    public void setOffHand(Item offHand) {
        this.offHand = offHand;
    }

    public void setArmor(Item armor) {
        this.armor = armor;
    }

    public boolean isEquipped(Item item) {
        if (mainHand == item)
            return true;
        if (offHand == item)
            return true;
        if (armor == item)
            return true;
        return false;
    }

    public boolean unequip(Item item) {
        boolean unequipSuccessful = false;
        if (mainHand == item) {
            mainHand = null;
            unequipSuccessful = true;
        }
        if (offHand == item) {
            offHand = null;
            unequipSuccessful = true;
        }
        if (armor == item) {
            armor = null;
            unequipSuccessful = true;
        }
        return unequipSuccessful;
    }

    public boolean equip(Engine gameContext, String itemName) {
        Item itemToEquip = getItem(itemName);
        if (itemToEquip == null)
            return false;
        if (isEquipped(itemToEquip))
            return false;
        itemToEquip.equip(gameContext, this);
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
