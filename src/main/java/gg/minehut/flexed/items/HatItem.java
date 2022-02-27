package gg.minehut.flexed.items;

import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class HatItem extends Item implements Serializable {
    public HatItem(String name, ItemStack icon, int price) {
        super(name, icon, price);
    }
}