package gg.minehut.flexed.items;

import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class HatItem extends Item {
    private final ItemStack item;

    public HatItem(String name, ItemStack icon, ItemStack item, int price) {
        super(name, icon, ItemCategory.HELMET, price);
        this.item = item;
    }
}